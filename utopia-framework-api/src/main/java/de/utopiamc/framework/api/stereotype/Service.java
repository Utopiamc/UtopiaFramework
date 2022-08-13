package de.utopiamc.framework.api.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * If present on a class, the class will be seen as a potential autowire candidate.
 */
@Stereotype
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Service {
}
