package de.utopiamc.framework.common.messaging.trafic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionRouter {

    private final Map<String, MessageListener> uuidToListenerMap = new HashMap<>();
    private final Map<String, List<MessageListener>> channelToListenerMap = new HashMap<>();


    /**
     * Register a route
     * @param channel The channel
     * @param subscriptionId THe UNIQUE ID for this listener
     * @param listener
     */
    public synchronized void register(String channel, String subscriptionId, MessageListener listener){

        // Unique id map
        uuidToListenerMap.put(subscriptionId, listener);

        // Channel map
        List<MessageListener> listeners = channelToListenerMap.get(channel);
        if(listeners == null){
            listeners = new ArrayList<>();
            channelToListenerMap.put(channel, listeners);
        }
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

}
