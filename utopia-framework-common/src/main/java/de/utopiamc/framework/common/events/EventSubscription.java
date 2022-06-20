package de.utopiamc.framework.common.events;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class EventSubscription {

    private final Class<?> subscribedEvent;
    private final Method method;

    public boolean hasSubscribedTo(Class<?> event) {
        return subscribedEvent.isAssignableFrom(event);
    }

    public Method getMethod() {
        return method;
    }
}
