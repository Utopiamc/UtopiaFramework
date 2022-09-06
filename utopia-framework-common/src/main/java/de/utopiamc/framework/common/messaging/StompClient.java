package de.utopiamc.framework.common.messaging;

import de.utopiamc.framework.common.messaging.listener.SocketListener;
import de.utopiamc.framework.common.messaging.listener.StompListener;
import de.utopiamc.framework.common.messaging.socket.StompSocket;
import de.utopiamc.framework.common.messaging.socket.StompWebSocket;
import de.utopiamc.framework.common.messaging.trafic.FrameType;
import de.utopiamc.framework.common.messaging.trafic.MessageListener;
import de.utopiamc.framework.common.messaging.trafic.StompFrame;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class StompClient {

    private static final Logger LOG = Logger.getLogger(StompClient.class.getSimpleName());

    public static StompClient connectOverWebSocket(String uri, StompListener listener) {
        try {
            URI serverUri = new URI(uri);
            StompWebSocket socket = new StompWebSocket(serverUri);
            return new StompClient(socket, listener);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final StompListener listener;
    private final StompSocket socket;

    private final SubscriptionRouter router = new SubscriptionRouter(this);

    private boolean isConnected;

    private StompClient(StompSocket webSocket, StompListener listener) {
        this.socket = webSocket;
        this.listener = listener;

        connect();
    }

    public void send(String channel, String message) {
        StompFrame frame = new StompFrame(FrameType.SEND, channel)
                .withHeader("Content-Type", "application/json")
                .withBody(message);
        sendStompFrame(frame);
    }

    public void subscribe(String channel, MessageListener listener) {
        String subscriptionId = UUID.randomUUID().toString();

        StompFrame frame = new StompFrame(FrameType.SUBSCRIBE)
                .withHeader("id", subscriptionId)
                .withHeader("destination", channel)
                .withHeader("ack", "auto");
        sendStompFrame(frame);

        router.register(channel, subscriptionId, listener);
    }

    private void connect() {
        this.socket.connect(new SocketListener() {
            @Override
            public void connected() {
                sendConnectFrame();
            }

            @Override
            public void onStompFrameReceived(StompFrame frame) {
                handleFrame(frame);
            }

            @Override
            public void closed(String reason) {
                LOG.info("Underling Websocket has closed: " + reason);
                handleServerDisconnected(reason);
            }

            @Override
            public void connectionFailed(Throwable exception) {
                LOG.warning(String.format("Underling Websocket could not connect! %s", exception.getMessage()));
                handleCanNotConnect(exception);
            }
        });
    }



    private void handleFrame(StompFrame frame) {
        switch (frame.getType()) {
            case CONNECTED -> handleConnected(frame);
            case ERROR -> handleErrorFrame(frame);
            case MESSAGE -> handleMessageFrame(frame);
            default -> LOG.warning(String.format("Unexpected STOMP Frame received: %s", frame.getType()));
        }
    }

    private void handleMessageFrame(StompFrame frame) {
        String channel = frame.getHeaderValue("destination");
        if(channel != null){
            router.routeMessage(channel, frame.getBody());
        }else{
            LOG.warning("Message frame was missing destination!");
        }
    }

    private void handleErrorFrame(StompFrame frame) {
        LOG.warning("Received STOMP error - connection get canceled now. %s" + frame);
        handleServerDisconnected("STOMP ERROR FRAME: " + frame.getBody());
    }

    private void handleServerDisconnected(String cause) {
        isConnected = false;
        fire(l -> l.disconnected(cause));
    }

    private void handleConnected(StompFrame frame) {
        isConnected = true;
        fire(l -> l.connectionSuccess(this));
    }

    void sendStompFrame(StompFrame frame) {
        socket.sendFrame(frame);
    }

    private void sendConnectFrame() {
        StompFrame frame = new StompFrame(FrameType.CONNECT)
                .withHeader("accept-version", "1.0,1.1,2.0")
                .withHeader("host", socket.getHost());

        sendStompFrame(frame);
    }


    public boolean isConnected() {
        return isConnected;
    }

    private void handleCanNotConnect(Throwable cause) {
        isConnected = false;
        fire(l -> l.connectionFailed(cause));
    }

    private void fire(Consumer<StompListener> action) {
        action.accept(listener);
    }

    public void disconnect() {
        socket.close();
    }

    public void unsubscribe(String channel) {
        router.unsubscribe(channel);
    }
}
