package de.utopiamc.framework.api.dropin.inject;

import de.utopiamc.framework.api.dropin.DropInBinder;

import java.util.Set;

public class ClassDetails {

    private final Class<?> type;

    private Object instance;

    private final Set<StereotypeResolver> resolvers;

    public ClassDetails(Class<?> type, Set<StereotypeResolver> resolvers) {
        this.type = type;
        this.resolvers = resolvers;
    }

    public void bind(DropInBinder binder) {
        binder.binder()
                .bind(type)
                .asEagerSingleton();

        resolvers.forEach(r -> r.resolve(type, binder));
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Object getInstance() {
        return instance;
    }
}
