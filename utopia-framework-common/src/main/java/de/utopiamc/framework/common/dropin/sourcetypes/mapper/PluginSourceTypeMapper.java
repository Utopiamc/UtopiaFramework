package de.utopiamc.framework.common.dropin.sourcetypes.mapper;

import de.utopiamc.framework.common.dropin.sourcetypes.PluginSourceType;
import de.utopiamc.framework.common.dropin.sourcetypes.SourceType;

public class PluginSourceTypeMapper implements SourceTypeMapper {

    @Override
    public SourceType getSourceType(Class<?> clazz) {
        return new PluginSourceType(clazz);
    }
}
