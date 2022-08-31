package de.utopiamc.framework.api.service;

import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.model.EventHandler;
import de.utopiamc.framework.api.model.TempEventSubscription;

public interface TempEventService {

    <T extends FrameworkEvent> TempEventSubscription subscribe(Class<T> event, EventHandler<T> handler);

}
