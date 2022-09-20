package de.utopiamc.framework.api.commands.descriptors;

import de.utopiamc.framework.api.commands.AllowedCaller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@CommandAnnotation
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Allow {

    AllowedCaller[] value();

}
