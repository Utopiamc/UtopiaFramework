package de.utopiamc.framework.api.entity;

import com.google.inject.Inject;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerServiceHolder {

    @Inject
    private static FrameworkPlayerService playerService;

    public static FrameworkPlayerService getPlayerService() {
        return playerService;
    }
}
