package de.utopiamc.framework.common.messaging.socket;

import de.utopiamc.framework.common.exceptions.StompParseException;
import de.utopiamc.framework.common.messaging.listener.SocketListener;
import de.utopiamc.framework.common.messaging.trafic.StompFrame;
import de.utopiamc.framework.common.messaging.trafic.StompFrameParser;
import jakarta.websocket.*;
import org.glassfish.tyrus.client.ClientManager;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

public class StompWebSocket implements StompSocket{

    private static final Logger LOG = Logger.getLogger(StompWebSocket.class.getSimpleName());

    private final StompFrameParser parser = new StompFrameParser();

    private final URI serverUri;
    private Session webSession;
    private SocketListener socketListener;
    public StompWebSocket(URI serverUri) {
        this.serverUri = serverUri;
    }

    @Override
    public void connect(SocketListener listener) {
        this.socketListener = listener;

        ClientEndpointConfig config = ClientEndpointConfig.Builder.create().build();
        ClientManager client = ClientManager.createClient();
        try {
            client.connectToServer(new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    webSession = session;

                    session.addMessageHandler(new MessageHandler.Whole<byte[]>() {
                        @Override
                        public void onMessage(byte[] message) {
                            onMessageReceived(message);
                        }
                    });
                    session.addMessageHandler(new MessageHandler.Whole<String>() {
                        @Override
                        public void onMessage(String message) {
                            onMessageReceived(message);
                        }
                    });

                    listener.connected();
                }

                @Override
                public void onClose(Session session, CloseReason closeReason) {
                    listener.closed(String.format("%s: %s", closeReason.getCloseCode(), closeReason.getReasonPhrase()));
                }

                @Override
                public void onError(Session session, Throwable thr) {
                    listener.closed(String.format("%s: %s", thr.getClass().getCanonicalName(), thr.getMessage()));
                }

            }, config, serverUri);
        } catch (DeploymentException | IOException e) {
            listener.connectionFailed(e);
            LOG.severe(String.format("Failed to connect to %s. %s", serverUri, e.getMessage()));
            e.printStackTrace();
        }
    }

    @Override
    public String getHost() {
        return serverUri.getHost();
    }

    @Override
    public boolean isConnected() {
        return webSession != null && webSession.isOpen();
    }

    @Override
    public void sendFrame(StompFrame frame) {
        if (isConnected()) {
            try {
                webSession.getBasicRemote().sendText(frame.toString());
            } catch (IOException e) {
                LOG.severe(String.format("Failed to send STOMP Frame. %s", e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    private void onMessageReceived(byte[] message) {
        try {
            emitFrameReceived(parser.parse(message));
        } catch (StompParseException e) {
            LOG.severe(String.format("Illegal STOMP Frame received! %s", e.getMessage()));
            e.printStackTrace();
        }
    }

    private void onMessageReceived(String message) {
        try {
            emitFrameReceived(parser.parse(message));
        } catch (StompParseException e) {
            LOG.severe(String.format("Illegal STOMP Frame received! %s", e.getMessage()));
            e.printStackTrace();
        }
    }

    private void emitFrameReceived(StompFrame frame) {
        if (socketListener != null)
            socketListener.onStompFrameReceived(frame);
    }

    public void close() {
        if (isConnected()) {
            try {
                webSession.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
