package de.utopiamc.framework.common.context;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.common.config.CommonConfigurationModule;
import de.utopiamc.framework.common.dropin.DropIn;
import de.utopiamc.framework.common.events.EventDispatchable;
import de.utopiamc.framework.common.inject.FrameworkModule;
import de.utopiamc.framework.common.models.TempEventSubscription;
import de.utopiamc.framework.common.old.dropin.DisableDropInReason;
import de.utopiamc.framework.common.old.dropin.DropInSource;
import de.utopiamc.framework.common.service.EventConverterService;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ApplicationContext implements Context, DropInHoldable, EventDispatchable {

    @Inject
    private static Logger logger;
    private final Set<DropIn> dropIns;
    private final Injector guiceInjector;
    private final EventConverterService eventConverter;

    private final Set<TempEventSubscription<?>> tempEventSubscriptions;

    public ApplicationContext(Set<DropIn> dropIns) {
        this(dropIns, List.of());
    }

    public ApplicationContext(Set<DropIn> dropIns, List<Module> modulesList) {
        this.dropIns = dropIns;
        this.tempEventSubscriptions = new HashSet<>();

        // Post Setup DropIns

        // Setup Guice
        Set<Module> modules = generateModules(modulesList);
        guiceInjector = createInjector(modules);

        eventConverter = getEventConverter();
    }

    public void disable() {
        dropIns.forEach(DropIn::disable);
    }

    //region DropIn Stuff
    private void disableDropIn(DropInSource dropInSource, DisableDropInReason reason) {
        dropInSource.disable();

        reason.log(logger);
    }
    //endregion

    //region Guice Stuff
    private Set<Module> generateModules(List<Module> modulesList) {
        Set<Module> modules = new HashSet<>(modulesList);

        modules.add(new FrameworkModule(this));
        modules.add(new CommonConfigurationModule());

        dropIns.forEach(d -> modules.add(d.createDropInModule()));

        return modules;
    }

    private Injector createInjector(Set<Module> modules) {
        return Guice.createInjector(modules);
    }
    //endregion

    //region DropInHoldable Implementation
    @Override
    public Set<DropIn> getDropIns() {
        return dropIns;
    }

    //endregion

    //region EventDispatchable Implementation
    public void dispatchEvent(Object event) {
        dispatchEvent(eventConverter.convertEvent(event));
    }

    public EventConverterService getEventConverter() {
        return guiceInjector.getInstance(EventConverterService.class);
    }

    @Override
    public void dispatchEvent(FrameworkEvent event) {
        dropIns.forEach(d -> d.dispatchEvent(event, this));

        handleTempEvents(event);
    }
    //endregion

    public Injector getGuiceInjector() {
        return guiceInjector;
    }

    public void addTempEventSubscription(TempEventSubscription<?> eventSubscription) {
        tempEventSubscriptions.add(eventSubscription);
    }

    public void removeTempEventSubscription(TempEventSubscription<?> eventSubscription) {
        tempEventSubscriptions.remove(eventSubscription);
    }

    private void handleTempEvents(FrameworkEvent event) {
        for (TempEventSubscription<?> tempEventSubscription : tempEventSubscriptions) {
            if (event.isComparable(tempEventSubscription.getEventClass())) {
                try {
                    for (Method declaredMethod : tempEventSubscription.getClass().getDeclaredMethods()) {
                        if (declaredMethod.getName().equalsIgnoreCase("handle")) {
                            event.callMethod(declaredMethod, tempEventSubscription);
                        }
                    }
                } catch (Throwable t) {
                    logger.severe(String.format("Failed to dispatch event. %s", t.getMessage()));
                    t.printStackTrace();
                }
            }
        }
    }

}
