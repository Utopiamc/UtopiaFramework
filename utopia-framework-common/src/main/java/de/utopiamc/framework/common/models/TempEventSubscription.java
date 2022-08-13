package de.utopiamc.framework.common.models;

public interface TempEventSubscription<T> {

    Class<T> getEventClass();

    void handle(T t);

}
