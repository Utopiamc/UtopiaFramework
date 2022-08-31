package de.utopiamc.framework.api.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public interface InjectableCandidate {

    Object get(Parameter type, Annotation annotation);

}
