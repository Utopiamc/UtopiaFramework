package de.utopiamc.framework.common.old.dropin;

import com.google.inject.Binder;
import de.utopiamc.framework.common.events.EventSubscription;

public interface DropInBinder {

    Binder binder();

    void addEventSubscription(EventSubscription eventSubscription);
}
