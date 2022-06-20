package de.utopiamc.framework.common.dropin.sourcetypes.mapper;

import de.utopiamc.framework.common.dropin.sourcetypes.ControllerSourceType;
import de.utopiamc.framework.common.dropin.sourcetypes.SourceType;

public class ControllerSourceTypeMapper implements SourceTypeMapper {

    @Override
    public SourceType getSourceType(Class<?> clazz) {
        return new ControllerSourceType(clazz);
    }
}
