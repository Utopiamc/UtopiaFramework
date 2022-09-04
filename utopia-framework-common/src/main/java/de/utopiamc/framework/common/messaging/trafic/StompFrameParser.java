package de.utopiamc.framework.common.messaging.trafic;

import de.utopiamc.framework.common.exceptions.StompParseException;
import de.utopiamc.framework.common.util.Pair;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StompFrameParser {

    private final Charset encoding = StandardCharsets.UTF_8;

    public StompFrame parse(String data) throws StompParseException {
        if(data == null) throw new IllegalArgumentException("data must not be NULL!");
        return parse(data.getBytes(encoding));
    }

    public StompFrame parse(ByteBuffer data) throws StompParseException {
        if(data == null) throw new IllegalArgumentException("data must not be NULL!");
        return parse(data.array());
    }

    public StompFrame parse(byte[] data) throws StompParseException {

        if(data == null) throw new IllegalArgumentException("data must not be NULL!");

        StompFrame frame = null;

        int p = 0;
        boolean inCommand = true;
        boolean inHeader = false;
        boolean previousWasEOL = false;
        boolean bodyStart = false;


        FrameType type = null;

        try {

            for (int i = 0; i <= data.length; i++) {
                boolean consumed = false;
                if (data[i] == StompFrame.PROTOCOL_EOL) {

                    // Skip CR after a LF
                    if(data.length > i+1 && data[i+1] == '\r' ){
                        i++;
                    }

                    if (!previousWasEOL) {
                        previousWasEOL = true;
                        // Command or header end here
                        if (inCommand) {
                            type = parseFrameType(data, p, i);
                            frame = new StompFrame(type);
                            inCommand = false;
                            inHeader = true;
                            consumed = true;
                        } else if (inHeader) {
                            Pair<String> header = parseHeader(data, p, i);
                            frame.withHeader(header.key, header.value);
                            inHeader = true;
                            consumed = true;
                        } else {

                        }
                    } else {
                        // Two EOLs - Body starts after this OR we are done
                        p++; // Skip this EOL
                        String body = parseBody(data, p, frame);
                        if(body != null) {
                            frame.withBody(body);
                        }
                        break;
                    }
                } else if (data[i] == StompFrame.PROTOCOL_END) {
                    previousWasEOL = false;
                    break;
                }else{
                    previousWasEOL = false;
                }

                if(consumed){
                    p = i + 1;
                }
            }
        }catch (Throwable cause){
            throw new StompParseException("Can not parse message:" + System.lineSeparator() + new String(data, encoding), cause);
        }

        return frame;
    }


    private String parseBody(byte[] data, int start, StompFrame frame) throws StompParseException {
        String strLenght = frame.getHeaderValue("content-length");
        int bodySize = -1;
        if(strLenght != null){
            bodySize = Integer.parseInt(strLenght);
        }

        String body;
        if(bodySize != -1) {
            body = copyBody(data, start, start + bodySize);
        }else{
            body = copyBody(data, start);
        }

        return body;
    }


    private Pair<String> parseHeader(byte[] data, int start, int end) {
        String word = copyWord(data, start, end);
        String[] parts = word.split(":");
        return new Pair<>(parts[0], parts[1]);
    }

    private FrameType parseFrameType(byte[] data, int start, int end){
        String word = copyWord(data, start, end);
        return Enum.valueOf(FrameType.class, word);
    }

    private String copyWord(byte[] data, int start, int end) {
        if(start < 0) throw new IllegalArgumentException("start "+start+" must be >= 0");
        if(end < 0) throw new IllegalArgumentException("end "+end+" must be >= 0");
        if(start >= data.length) throw new IllegalArgumentException("start index "+start+" was bigger than the whole data " + data.length);
        if(end < start) throw new IllegalArgumentException("end "+end+" must be bigger than start index " + start);
        if(end >= data.length) throw new IllegalArgumentException("end index "+end+" was bigger than the whole data " + data.length);

        return new String(Arrays.copyOfRange(data, start, end), encoding);
    }

    private String copyBody(byte[] data, int start) throws StompParseException {

        // find the end of the body, which is specified by a NULL byte
        int end = -1;
        for(int i=0; data.length > i; i++){
            if(data[i] == StompFrame.PROTOCOL_END){
                end = i;
                break;
            }
        }
        if(end != -1) {
            return copyBody(data, start, end);
        }else{
            throw new StompParseException("Body does not end with NULL Byte!");
        }
    }

    private String copyBody(byte[] data, int start, int end) {
        return copyWord(data, start, end);
    }

}
