package de.utopiamc.framework.api.context;

import com.google.inject.Injector;
import de.utopiamc.framework.api.entity.FrameworkPlayer;

import java.util.UUID;

public interface Context extends EventDispatchable {

    default Injector getGuiceInjector() {
        throw new IllegalStateException();
    }

    <T> T getInstance(Class<T> type);

    FrameworkPlayer getFrameworkPlayer(UUID holder);
}
