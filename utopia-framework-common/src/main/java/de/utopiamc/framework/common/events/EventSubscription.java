package de.utopiamc.framework.common.events;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@RequiredArgsConstructor
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
