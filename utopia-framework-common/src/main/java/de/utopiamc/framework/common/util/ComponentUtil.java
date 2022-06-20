package de.utopiamc.framework.common.util;

import de.utopiamc.framework.api.inject.components.Configuration;
import de.utopiamc.framework.api.inject.components.Controller;
import de.utopiamc.framework.api.inject.components.Plugin;
import de.utopiamc.framework.common.dropin.sourcetypes.mapper.ConfigurationSourceTypeMapper;
import de.utopiamc.framework.common.dropin.sourcetypes.mapper.ControllerSourceTypeMapper;
import de.utopiamc.framework.common.dropin.DropInSourceComponent;
import de.utopiamc.framework.common.dropin.sourcetypes.SourceType;
import de.utopiamc.framework.common.dropin.sourcetypes.mapper.PluginSourceTypeMapper;
import de.utopiamc.framework.common.dropin.sourcetypes.mapper.SourceTypeMapper;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.util.Map;

@UtilityClass
public class ComponentUtil {

    static {
        SOURCE_TYPES = Map.of(
                Controller.class, new ControllerSourceTypeMapper(),
                Plugin.class, new PluginSourceTypeMapper(),
                Configuration.class, new ConfigurationSourceTypeMapper());
    }

    private final static Map<Class<? extends Annotation>, SourceTypeMapper> SOURCE_TYPES;

    public static DropInSourceComponent toSourceComponent(Class<?> aClass) {
        return new DropInSourceComponent(aClass);
    }

    public static SourceType sourceAnnotationToSourceType(Annotation annotation, Class<?> clazz) {
        SourceTypeMapper mapper = SOURCE_TYPES.get(annotation.annotationType());
        return mapper != null ? mapper.getSourceType(clazz) : null;
    }
}
