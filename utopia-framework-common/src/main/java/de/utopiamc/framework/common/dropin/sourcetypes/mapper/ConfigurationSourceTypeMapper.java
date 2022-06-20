package de.utopiamc.framework.common.dropin.sourcetypes.mapper;

import de.utopiamc.framework.common.dropin.sourcetypes.ConfigurationSourceType;
import de.utopiamc.framework.common.dropin.sourcetypes.SourceType;

public class ConfigurationSourceTypeMapper implements SourceTypeMapper {

    @Override
    public SourceType getSourceType(Class<?> clazz) {
        return new ConfigurationSourceType(clazz);
    }
}
