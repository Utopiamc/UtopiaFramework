package de.utopiamc.framework.common.models;

import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.event.qualifier.Event;

public interface TempEventSubscription<T extends FrameworkEvent> {

    Class<T> getEventClass();

    void handle(@Event T t);


}
