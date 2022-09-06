package de.utopiamc.framework.common.messaging;

import de.utopiamc.framework.api.tasks.TaskService;
import de.utopiamc.framework.common.messaging.listener.StompListener;
import de.utopiamc.framework.common.messaging.packets.PacketHandler;
import de.utopiamc.framework.common.messaging.trafic.MessageListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Singleton
public class StompHandler {

    private static final Logger LOG = Logger.getLogger(StompHandler.class.getSimpleName());

    private final TaskService taskService;
    private final PacketHandler packetHandler;

    private StompClient client;
    private final Map<String, MessageListener> queuedSubscriptions;

    @Inject
    public StompHandler(TaskService taskService, PacketHandler packetHandler) {
        this.taskService = taskService;
        this.packetHandler = packetHandler;
        this.queuedSubscriptions = Collections.synchronizedMap(new HashMap<>());
    }

    @Inject
    private void connect() {
        client = StompClient.connectOverWebSocket("ws://localhost:8080/events", new StompListener() {
            @Override
            public void connectionSuccess(StompClient connection) {
                doSubscriptions();
            }

            @Override
            public void connectionFailed(Throwable cause) {
                disconnect();
            }

            @Override
            public void disconnected(String reason) {
                disconnect();
            }
        });
    }

    public void disconnect() {
        try {
            client.disconnect();
        }catch (Throwable t) {
            LOG.severe(String.format("Failed to disconnect client. %s", t.getMessage()));
        }
    }

    public void subscribe(UUID player) {
        subscribe("/player/%s".formatted(player), packetHandler::handlePlayer);
    }

    public void unsubscribe(UUID player) {
        unsubscribe("/player/%s".formatted(player));
    }

    public void unsubscribe(String channel) {
        if (client.isConnected()) {
            queuedSubscriptions.remove(channel);
            client.unsubscribe(channel);
        }
    }

    private synchronized void subscribe(String channel, MessageListener handler) {
        queuedSubscriptions.put(channel, handler);
        doSubscriptions();
    }

    private synchronized void doSubscriptions() {
        if (!client.isConnected())
            return;

        for (Map.Entry<String, MessageListener> subscription : queuedSubscriptions.entrySet()) {
            taskService.runAsync(() -> {
               client.subscribe(subscription.getKey(), subscription.getValue());
            });
            queuedSubscriptions.remove(subscription.getKey());
        }
    }

}
