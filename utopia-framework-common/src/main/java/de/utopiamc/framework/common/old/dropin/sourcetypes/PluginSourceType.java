package de.utopiamc.framework.common.old.dropin.sourcetypes;

import de.utopiamc.framework.api.dropin.annotations.OnDisable;
import de.utopiamc.framework.api.dropin.annotations.OnEnable;
import de.utopiamc.framework.api.dropin.annotations.OnLoad;
import de.utopiamc.framework.api.event.lifecycle.OnDisableEvent;
import de.utopiamc.framework.api.event.lifecycle.OnEnableEvent;
import de.utopiamc.framework.api.event.lifecycle.OnLoadEvent;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.common.old.dropin.DropInBinder;
import de.utopiamc.framework.common.events.EventSubscription;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class PluginSourceType extends SourceType {

    private final Set<EventSubscription> eventSubscriptions;

    public PluginSourceType(Class<?> type) {
        super(type);

        this.eventSubscriptions = new HashSet<>();

        indexLifecycleMethods();
    }

    private void indexLifecycleMethods() {
        for (Method declaredMethod : type.getDeclaredMethods()) {
            OnEnable onEnable = AnnotationUtil.getAnnotation(declaredMethod, OnEnable.class);
            if (onEnable != null) {
                eventSubscriptions.add(new EventSubscription(OnEnableEvent.class, declaredMethod));
                continue;
            }

            OnDisable onDisable = AnnotationUtil.getAnnotation(declaredMethod, OnDisable.class);
            if (onDisable != null) {
                eventSubscriptions.add(new EventSubscription(OnDisableEvent.class, declaredMethod));
                continue;
            }

            OnLoad onLoad = AnnotationUtil.getAnnotation(declaredMethod, OnLoad.class);
            if (onLoad != null) {
                eventSubscriptions.add(new EventSubscription(OnLoadEvent.class, declaredMethod));
            }
        }
    }

    @Override
    public void bind(DropInBinder module) {
        eventSubscriptions.forEach(module::addEventSubscription);
    }

}
