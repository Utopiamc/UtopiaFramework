package de.utopiamc.framework.api.dropin.inject;


import de.utopiamc.framework.api.dropin.DropInBinder;

@FunctionalInterface
public interface StereotypeResolver {

    void resolve(Class<?> cls, DropInBinder binder);

}
