package de.utopiamc.framework.common.dropin.sourcetypes.mapper;

import de.utopiamc.framework.common.dropin.sourcetypes.SourceType;

public interface SourceTypeMapper {

    SourceType getSourceType(Class<?> clazz);

}
