package de.utopiamc.framework.common.service.impl;

import com.google.inject.Inject;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.model.EventHandler;
import de.utopiamc.framework.api.service.TempEventService;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.common.models.TempEventSubscription;

public class CommonTempEventService implements TempEventService {

    private final ApplicationContext applicationContext;

    @Inject
    public CommonTempEventService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T extends FrameworkEvent> de.utopiamc.framework.api.model.TempEventSubscription subscribe(Class<T> event, EventHandler<T> handler) {
        TempEventSubscription<T> eventSubscription = new TempEventSubscription<T>() {
            @Override
            public Class<T> getEventClass() {
                return event;
            }

            @Override
            public void handle(T t) {
                handler.handle(t);
            }
        };

        applicationContext.addTempEventSubscription(eventSubscription);

        return () -> {
            applicationContext.removeTempEventSubscription(eventSubscription);
        };
    }
}
