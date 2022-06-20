package de.utopiamc.framework.api.inject.components;

import de.utopiamc.framework.api.inject.annotations.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Component
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Controller {

}
