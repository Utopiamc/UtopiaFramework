package de.utopiamc.framework.api.events.player.qualifier;

import de.utopiamc.framework.api.inject.annotations.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Qualifier
public @interface Player {
}
