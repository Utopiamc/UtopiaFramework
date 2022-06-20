package de.utopiamc.framework.common.dropin.sourcetypes;

import de.utopiamc.framework.api.config.UtopiaConfigurationClass;
import de.utopiamc.framework.common.dropin.DropInModule;
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
    public void bind(DropInModule module) {
        System.out.println(interfaces);
        interfaces.forEach(i -> module.ppBind(i, type));
    }
}
