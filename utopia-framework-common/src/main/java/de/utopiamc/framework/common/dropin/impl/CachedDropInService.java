package de.utopiamc.framework.common.dropin.impl;

import com.google.inject.Inject;
import de.utopiamc.framework.api.stereotype.Stereotype;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.api.dropin.DropInService;
import de.utopiamc.framework.api.dropin.inject.ClassDetails;
import de.utopiamc.framework.api.dropin.inject.StereotypeResolver;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static de.utopiamc.framework.common.checks.Assert.assertTrue;

public class CachedDropInService implements DropInService {

    private final Map<Class<? extends Annotation>, StereotypeResolver> stereotypes;

    @Inject
    public CachedDropInService() {
        this.stereotypes = new HashMap<>();
    }

    @Override
    public void addStereotype(Class<? extends Annotation> identifier, StereotypeResolver resolver) {
        assertTrue(AnnotationUtil.isAnnotationPresent(identifier, Stereotype.class), "Invalid identifier. Identifiers have to be annotated with @Stereotype");

        stereotypes.put(identifier, resolver);
    }

    @Override
    public Set<ClassDetails> scanClasses(Set<Class<?>> classes) {
        return classes.stream()
                .map(this::scanClass)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private ClassDetails scanClass(Class<?> cls) {
        Set<StereotypeResolver> resolvers = AnnotationUtil.getAnnotationsWithAnnotation(cls, Stereotype.class).stream()
                .map((s) -> this.stereotypes.get(s.annotationType()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (resolvers.isEmpty())
            return null;

        return new ClassDetails(cls, resolvers);
    }

}
