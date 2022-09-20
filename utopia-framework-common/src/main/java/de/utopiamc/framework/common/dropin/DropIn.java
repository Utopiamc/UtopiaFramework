package de.utopiamc.framework.common.dropin;

import com.google.inject.Binder;
import com.google.inject.Module;
import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.dropin.DropInBinder;
import de.utopiamc.framework.api.dropin.inject.ClassDetails;
import de.utopiamc.framework.api.event.EventSubscription;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.common.models.JarFileIndex;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public final class DropIn {

    private final Logger logger;

    private final Set<Runnable> tasks = new HashSet<>();

    private final Set<ClassDetails> classDetails;
    private final Set<EventSubscription> eventSubscriptions;

    public DropIn(Set<ClassDetails> classDetails, JarFileIndex index) {
        this.classDetails = classDetails;
        this.eventSubscriptions = new HashSet<>();

        this.logger = Logger.getLogger(index.name());
    }

    public void configure(Binder binder) {
        DropInBinder dropInBinder = new DropInBinder() {

            @Override
            public Binder binder() {
                return binder;
            }

            @Override
            public void addEventSubscription(EventSubscription eventSubscription) {
                eventSubscriptions.add(eventSubscription);
            }

            @Override
            public void registerTask(Runnable runnable) {
                tasks.add(runnable);
            }
        };

        for (ClassDetails classDetail : classDetails) {
            classDetail.bind(dropInBinder);
        }
    }

    public Module createDropInModule() {
        return new DropInModule(this);
    }

    public void enable(Context context) {
        for (Runnable task : tasks) {
            if (task instanceof CommandRegisterTask)
                ((CommandRegisterTask) task).setContext(context);
            task.run();
        }
    }

    public void dispatchEvent(FrameworkEvent event, ApplicationContext context) {
        for (EventSubscription eventSubscription : eventSubscriptions) {
            if (event.isComparable(eventSubscription.getSubscribedEvent())) {
                try {
                    event.callMethod(eventSubscription.getMethod(),
                            context.getGuiceInjector().
                                    getInstance(eventSubscription.getMethod().getDeclaringClass()));
                }catch (Throwable t) {
                    logger.severe(String.format("Failed to dispatch event. '%s' thrown an exception. %s", eventSubscription.getMethod().getName(), t.getMessage()));
                    t.printStackTrace();
                }
            }
        }
    }

    public void disable() {

    }
}
