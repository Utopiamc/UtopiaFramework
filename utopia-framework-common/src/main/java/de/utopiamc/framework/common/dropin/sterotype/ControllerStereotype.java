package de.utopiamc.framework.common.dropin.sterotype;

import de.utopiamc.framework.api.dropin.DropInBinder;
import de.utopiamc.framework.api.dropin.inject.StereotypeResolver;
import de.utopiamc.framework.api.event.EventSubscription;
import de.utopiamc.framework.api.event.Subscribe;
import de.utopiamc.framework.api.util.AnnotationUtil;

import java.lang.reflect.Method;

public class ControllerStereotype implements StereotypeResolver {

    @Override
    public void resolve(Class<?> cls, DropInBinder binder) {
        for (Method declaredMethod : cls.getDeclaredMethods()) {
            Subscribe annotation = AnnotationUtil.getAnnotation(declaredMethod, Subscribe.class);
            if (annotation != null) {
                registerSubscription(binder, annotation, declaredMethod);
            }
        }
    }

    private void registerSubscription(DropInBinder binder, Subscribe subscribe, Method method) {
        method.setAccessible(true);
        binder.addEventSubscription(new EventSubscription(subscribe.event(), method));
    }

}
