package de.utopiamc.framework.api.dropin;

import de.utopiamc.framework.api.dropin.inject.ClassDetails;
import de.utopiamc.framework.api.dropin.inject.StereotypeResolver;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface DropInService {

    void addStereotype(Class<? extends Annotation> identifier, StereotypeResolver resolver);

    Set<ClassDetails> scanClasses(Set<Class<?>> classes);

}
