package de.utopiamc.framework.common.dropin;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.config.UtopiaConfigurationClass;
import de.utopiamc.framework.common.events.EventSubscription;
import lombok.ToString;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
public class DropInModule extends AbstractModule {

    @ToString.Exclude
    private final DropIn dropIn;
    private final Set<EventSubscription> subscriptions;

    public DropInModule(DropIn dropIn) {
        this.dropIn = dropIn;

        this.subscriptions = new HashSet<>();
    }

    @Override
    protected void configure() {
        dropIn.bind(this);
    }

    public void pBind(Class<?> cls) {
        bind(cls).asEagerSingleton();
    }

    public void addEventSubscriptions(Set<EventSubscription> subscription) {
        subscriptions.addAll(subscription);
    }

    public Set<Method> getListeners(Class<?> event) {
        return subscriptions.stream().filter(s -> s.hasSubscribedTo(event)).map(EventSubscription::getMethod).collect(Collectors.toSet());
    }

    public <T> void pBind(Class<T> type, Class<? extends T> to) {
        bind(type).to(to);
    }

    public <T> void ppBind(Class<T> i, Class<?> type) {
        pBind(i, (Class<? extends T>) type);
    }
}
