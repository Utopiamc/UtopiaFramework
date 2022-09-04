package de.utopiamc.framework.common.messaging.trafic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class StompFrame {

    public final static byte PROTOCOL_EOL = 0x0A;
    public final static byte PROTOCOL_END = 0x00;


    private final FrameType command;
    private final Map<String, String> headers = new HashMap<>();
    private String body;

    public StompFrame(FrameType command, String channel){
        this.command = command;
        withHeader("destination", channel);
    }

    public StompFrame(FrameType command){
        this.command = command;
    }

    /**
     * Add the header value to this frame
     * @param key
     * @param value
     * @return
     */
    public StompFrame withHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Set the body for this frame. (Overwrites an existing body)
     * @param message
     * @return
     */
    public StompFrame withBody(String message) {
        this.body = message;
        return this;
    }

    public FrameType getType(){
        return command;
    }

    /**
     * Returns the value of the requested header, or null if not present
     * @param key
     * @return
     */
    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    private final Charset encoding = StandardCharsets.UTF_8;

    /**
     * Returns the framw as byte array ready to be sent
     * @return
     */
    public ByteBuffer toByteBuffer(){
        ByteArrayOutputStream sbuf = new ByteArrayOutputStream(250);

        byte[] bodyData = (body != null) ? body.getBytes(encoding) : new byte[0];

        try {
            // COMMAND & EOL
            sbuf.write(command.toString().getBytes(encoding));
            sbuf.write(PROTOCOL_EOL);

            // HEADERS
            {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    // HEADER & EOL
                    String headerStr = header.getKey() + ":" + header.getValue();
                    sbuf.write(headerStr.getBytes(encoding));
                    sbuf.write(PROTOCOL_EOL);
                }
                // Content length
                String headerStr = "content-length:" + bodyData.length;
                sbuf.write(headerStr.getBytes(encoding));
                sbuf.write(PROTOCOL_EOL);
            }
            // Double EOL which ends the header and starts body
            sbuf.write(PROTOCOL_EOL);

            // BODY
            sbuf.write(bodyData);

            // END FRAME
            sbuf.write(PROTOCOL_END);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap( sbuf.toByteArray() );
    }

    @Override
    public String toString(){
        return new String(toByteBuffer().array(), encoding);
    }


    public String getBody() {
        return body;
    }

    public String getHeaderValues() {
        return headers.toString();
    }
}
