package de.utopiamc.framework.api.inject;

import com.google.inject.Injector;
import com.google.inject.Key;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

@RequiredArgsConstructor
public class InjectableGuiceCandidate implements InjectableCandidate {

    private final Injector injector;

    @Override
    public Object get(Parameter type, Annotation annotations) {
        try {
            return injector.getInstance(Key.get(type.getParameterizedType(), annotations));
        }catch (IllegalArgumentException ignored) {
            return null;
        }
    }

}
