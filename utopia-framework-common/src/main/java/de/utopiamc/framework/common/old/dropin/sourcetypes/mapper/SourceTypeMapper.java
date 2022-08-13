package de.utopiamc.framework.common.old.dropin.sourcetypes.mapper;

import de.utopiamc.framework.common.old.dropin.sourcetypes.SourceType;

public interface SourceTypeMapper {

    SourceType getSourceType(Class<?> clazz);

}
