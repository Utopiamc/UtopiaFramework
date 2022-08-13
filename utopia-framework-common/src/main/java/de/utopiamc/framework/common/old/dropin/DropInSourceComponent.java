package de.utopiamc.framework.common.old.dropin;

import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.common.old.dropin.sourcetypes.SourceType;
import de.utopiamc.framework.common.util.ComponentUtil;
import de.utopiamc.framework.api.inject.annotations.Component;
import lombok.Data;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class DropInSourceComponent {

    private Set<SourceType> sourceTypes;

    private final Class<?> type;

    public DropInSourceComponent(Class<?> type) {
        this.type = type;

        index();
    }

    private void index() {
        sourceTypes = AnnotationUtil.getAnnotationsWithAnnotation(type, Component.class).stream()
                .map(annotation -> ComponentUtil.sourceAnnotationToSourceType(annotation, type))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public void bind(DropInBinder binder) {
        binder.binder().bind(type).asEagerSingleton();

        sourceTypes.forEach(t -> t.bind(binder));
    }
}
