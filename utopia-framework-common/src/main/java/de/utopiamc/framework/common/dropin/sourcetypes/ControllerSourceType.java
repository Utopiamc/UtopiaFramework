package de.utopiamc.framework.common.dropin.sourcetypes;

import de.utopiamc.framework.api.event.Subscribe;
import de.utopiamc.framework.common.dropin.DropInModule;
import de.utopiamc.framework.common.events.EventSubscription;
import de.utopiamc.framework.api.util.AnnotationUtil;
import lombok.ToString;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@ToString
public class ControllerSourceType extends SourceType {

    private final Set<EventSubscription> eventSubscriptions;

    public ControllerSourceType(Class<?> type) {
        super(type);

        this.eventSubscriptions = new HashSet<>();

        indexSubscriptions();
    }

    private void indexSubscriptions() {
        for (Method declaredMethod : type.getDeclaredMethods()) {
            Subscribe annotation = AnnotationUtil.getAnnotation(declaredMethod, Subscribe.class);
            if (annotation != null) {
                System.out.println(annotation);
                eventSubscriptions.add(new EventSubscription(annotation.event(), declaredMethod));
            }
        }
    }

    @Override
    public void bind(DropInModule module) {
        module.addEventSubscriptions(eventSubscriptions);
    }
}
