package de.utopiamc.framework.api.dropin;

import com.google.inject.Binder;
import de.utopiamc.framework.api.event.EventSubscription;

public interface DropInBinder {

    Binder binder();

    void addEventSubscription(EventSubscription eventSubscription);

    void registerTask(Runnable runnable);
}

