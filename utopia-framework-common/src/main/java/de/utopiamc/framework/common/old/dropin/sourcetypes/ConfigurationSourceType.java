package de.utopiamc.framework.common.old.dropin.sourcetypes;

import com.google.inject.Binder;
import de.utopiamc.framework.api.config.UtopiaConfigurationClass;
import de.utopiamc.framework.common.old.dropin.DropInBinder;
import de.utopiamc.framework.common.exceptions.InvalidConfigurationClassException;
import de.utopiamc.framework.common.util.ReflectionUtil;

import java.util.Set;

public class ConfigurationSourceType extends SourceType {

    private Set<Class<? extends UtopiaConfigurationClass>> interfaces;

    public ConfigurationSourceType(Class<?> type) {
        super(type);

        checkConfiguration();
    }

    private void checkConfiguration() {
        interfaces = ReflectionUtil.getInterfaces(type, UtopiaConfigurationClass.class);
        if (interfaces.isEmpty())
            throw new InvalidConfigurationClassException(String.format("'%s', has no interfaces that extend '%s'", type, UtopiaConfigurationClass.class));
    }

    @Override
    public void bind(DropInBinder module) {
        interfaces.forEach(i -> bindInterface(module.binder(), i));
    }

    @SuppressWarnings("unchecked")
    private <T> void bindInterface(Binder binder, Class<T> interfaces) {
        binder.bind(interfaces).to((Class<? extends T>) type);
    }
}
