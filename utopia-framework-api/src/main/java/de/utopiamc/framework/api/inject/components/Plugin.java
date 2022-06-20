package de.utopiamc.framework.api.inject.components;

import de.utopiamc.framework.api.inject.annotations.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This {@link } annotation should be placed on a Plugin class. The metadata of the given dropin will be added to the plugins list.
 *
 * NOTE: There is no need for a {@link Plugin} annotation. Your dropin will work the same, it just doesn't get displayed in the plugins list.
 * @author DevOskar
 */
@Component
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Plugin {

    /**
     * Name of the dropin. When kept empty the {@link Class#getSimpleName()} name is used.
     * @return the custom name of the dropin.
     */
    String name() default "";

    /**
     * Name of the dropin. When kept empty 1.0-SNAPSHOT is used.
     *
     * NOTE: Please use RELEASE, SNAPSHOT, ALPHA or BETA in the end. In production RELEASE should be used.
     * @return the current version of this dropin.
     */
    String version() default "";

    /**
     * Names of the authors.
     * NOTE: Your code always should be documented with an @author tag!
     * @return all authors of this dropin. They will be displayed in the plugins list.
     */
    String[] author() default "";

}
