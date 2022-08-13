package de.utopiamc.framework.common.dropin.sterotype;

import com.google.inject.Binder;
import de.utopiamc.framework.api.dropin.DropInBinder;
import de.utopiamc.framework.api.dropin.inject.StereotypeResolver;
import de.utopiamc.framework.common.util.ReflectionUtil;

import java.util.Set;

public class ServiceStereotype implements StereotypeResolver {

    @Override
    public void resolve(Class<?> cls, DropInBinder binder) {
        Set<Class<?>> interfaces = ReflectionUtil.getInterfaces(cls);

        for (Class<?> anInterface : interfaces) {
            bindInterface(binder.binder(), anInterface, cls);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void bindInterface(Binder binder, Class<T> anInterface, Class<?> cls) {
        binder.bind(anInterface).to((Class<? extends T>) cls);
    }
}
