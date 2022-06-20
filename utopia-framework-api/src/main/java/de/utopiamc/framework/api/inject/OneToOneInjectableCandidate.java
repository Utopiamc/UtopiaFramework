package de.utopiamc.framework.api.inject;

import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

@RequiredArgsConstructor
public class OneToOneInjectableCandidate implements InjectableCandidate{

    private final Class<? extends Annotation> annotation;
    private final Object object;

    @Override
    public Object get(Parameter type, Annotation annotations) {
        if (type.getType().isAssignableFrom(object.getClass()) && annotations.annotationType().equals(annotation)) {
            return object;
        }

        return null;
    }
}
