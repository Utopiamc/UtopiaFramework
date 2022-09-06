package de.utopiamc.framework.common.messaging;

import de.utopiamc.framework.common.messaging.trafic.FrameType;
import de.utopiamc.framework.common.messaging.trafic.MessageListener;
import de.utopiamc.framework.common.messaging.trafic.StompFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionRouter {

    private final Map<MessageListener, String> uuidToListenerMap = new HashMap<>();
    private final Map<String, List<MessageListener>> channelToListenerMap = new HashMap<>();

    private final StompClient client;

    public SubscriptionRouter(StompClient client) {
        this.client = client;
    }

    /**
     * Register a route
     * @param channel The channel
     * @param subscriptionId THe UNIQUE ID for this listener
     * @param listener
     */
    public synchronized void register(String channel, String subscriptionId, MessageListener listener){

        // Unique id map
        uuidToListenerMap.put(listener, subscriptionId);

        // Channel map
        List<MessageListener> listeners = channelToListenerMap.computeIfAbsent(channel, k -> new ArrayList<>());
        listeners.add(listener);
    }

    /**
     * Routes the given message to the given channel and notify all
     * listeners of this channel.
     * @param channel
     * @param message
     */
    public synchronized void routeMessage(String channel, String message){
        List<MessageListener> listeners = channelToListenerMap.get(channel);
        if(listeners != null){
            for(MessageListener l : listeners){
                l.messageReceived(message);
            }
        }
    }

    public synchronized void unsubscribe(String channel) {
        List<MessageListener> remove = channelToListenerMap.remove(channel);
        for (MessageListener messageListener : remove) {
            String id = uuidToListenerMap.remove(messageListener);
            StompFrame frame = new StompFrame(FrameType.UNSUBSCRIBE)
                    .withHeader("id", id);
            client.sendStompFrame(frame);
        }
    }

}
