package de.utopiamc.framework.api.event;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class EventSubscription {

    private final Class<?> subscribedEvent;
    private final Method method;

    public Method getMethod() {
        return method;
    }

    public Class<?> getSubscribedEvent() {
        return subscribedEvent;
    }

}
