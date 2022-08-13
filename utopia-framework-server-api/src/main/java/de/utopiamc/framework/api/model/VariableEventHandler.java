package de.utopiamc.framework.api.model;


import de.utopiamc.framework.api.entity.FrameworkPlayer;

@FunctionalInterface
public interface VariableEventHandler<E> {

    String handle(FrameworkPlayer player, Object event);

}
