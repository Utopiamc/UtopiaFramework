package de.utopiamc.framework.api.context;

import com.google.inject.Injector;
import de.utopiamc.framework.api.entity.FrameworkPlayer;

import java.util.UUID;

public interface Context extends EventDispatchable {

    Injector getGuiceInjector();

    FrameworkPlayer getFrameworkPlayer(UUID holder);
}
