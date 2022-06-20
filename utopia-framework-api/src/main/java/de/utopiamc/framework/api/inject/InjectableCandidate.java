package de.utopiamc.framework.api.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public interface InjectableCandidate {

    Object get(Parameter type, Annotation annotations);

}
