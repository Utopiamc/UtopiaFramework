package de.utopiamc.framework.api.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class MatchingInjectableCandidate implements InjectableCandidate {

    private final Class<? extends Annotation> annotation;
    private final Object[] types;

    public MatchingInjectableCandidate(Class<? extends Annotation> annotation, Object... types) {
        this.annotation = annotation;
        this.types = types;
    }

    @Override
    public Object get(Parameter type, Annotation annotations) {
        if (annotations.annotationType().equals(annotation)) {
            for (Object o : types) {
                if (type.getType().isAssignableFrom(o.getClass())) {
                    return o;
                }
            }
        }

        return null;
    }
}
