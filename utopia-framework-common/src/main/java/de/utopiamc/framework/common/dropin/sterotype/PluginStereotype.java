package de.utopiamc.framework.common.dropin.sterotype;

import de.utopiamc.framework.api.dropin.DropInBinder;
import de.utopiamc.framework.api.dropin.annotations.OnDisable;
import de.utopiamc.framework.api.dropin.annotations.OnEnable;
import de.utopiamc.framework.api.dropin.annotations.OnLoad;
import de.utopiamc.framework.api.dropin.inject.StereotypeResolver;
import de.utopiamc.framework.api.event.lifecycle.OnDisableEvent;
import de.utopiamc.framework.api.event.lifecycle.OnEnableEvent;
import de.utopiamc.framework.api.event.EventSubscription;
import de.utopiamc.framework.api.util.AnnotationUtil;

import java.lang.reflect.Method;

public class PluginStereotype implements StereotypeResolver {

    @Override
    public void resolve(Class<?> cls, DropInBinder binder) {
        for (Method declaredMethod : cls.getDeclaredMethods()) {
            if (AnnotationUtil.isAnnotationPresent(declaredMethod, OnEnable.class)) {
                declaredMethod.setAccessible(true);
                binder.addEventSubscription(new EventSubscription(OnEnableEvent.class, declaredMethod));
            }
            if (AnnotationUtil.isAnnotationPresent(declaredMethod, OnDisable.class)) {
                declaredMethod.setAccessible(true);
                binder.addEventSubscription(new EventSubscription(OnDisableEvent.class, declaredMethod));
            }
            if (AnnotationUtil.isAnnotationPresent(declaredMethod, OnLoad.class)) {
                declaredMethod.setAccessible(true);
                binder.addEventSubscription(new EventSubscription(OnEnableEvent.class, declaredMethod));
            }
        }
    }

}
