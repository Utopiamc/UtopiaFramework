package de.utopiamc.framework.api.model;

@FunctionalInterface
public interface EventHandler<T> {

    void handle(T event);

}
