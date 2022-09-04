package de.utopiamc.framework.worker.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class EventPublishService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public EventPublishService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void publishPlayer(UUID player, Object message) {
        GsonMessageConverter converter = new GsonMessageConverter();
        Message<?> iMessage = converter.toMessage(message, new MessageHeaders(new HashMap<>()));
        if (iMessage == null)
            return;
        messagingTemplate.send("/player/%s".formatted(player), iMessage);
    }

    public void publishGlobal(Object message) {
        messagingTemplate.convertAndSend("/topic/global", message);
    }

}
