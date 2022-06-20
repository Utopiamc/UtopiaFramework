package de.utopiamc.framework.api.inject.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Used to determine which dependency candidate should be injected.
 * @author DevOskar
 */
@Inherited
@Documented
@BindingAnnotation
@Target({ANNOTATION_TYPE, FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Qualifier {

    String value() default "";

}
