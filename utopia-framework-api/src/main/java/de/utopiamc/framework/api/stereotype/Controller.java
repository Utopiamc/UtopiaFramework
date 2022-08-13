package de.utopiamc.framework.api.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Stereotype
@Target(TYPE)
@Retention(RUNTIME)
public @interface Controller {

}
