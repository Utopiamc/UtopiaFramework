package de.utopiamc.framework.common.dropin.sterotype;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import de.utopiamc.framework.api.config.UtopiaConfigurationClass;
import de.utopiamc.framework.api.dropin.DropInBinder;
import de.utopiamc.framework.api.dropin.inject.StereotypeResolver;
import de.utopiamc.framework.common.util.ReflectionUtil;

public class ConfigurationStereotype implements StereotypeResolver {

    @Override
    public void resolve(Class<?> cls, DropInBinder binder) {
        Class<? extends UtopiaConfigurationClass> anInterface = ReflectionUtil.getInterface(cls, UtopiaConfigurationClass.class);

        if (anInterface == null)
            return;

        bindConfig(anInterface, binder.binder(), cls);
    }

    @SuppressWarnings("unchecked")
    private <C extends UtopiaConfigurationClass> void bindConfig(Class<C> anInterface, Binder binder, Class<?> cls) {
        Multibinder.newSetBinder(binder, anInterface).addBinding()
                .to((Class<? extends C>) cls);
    }

}
