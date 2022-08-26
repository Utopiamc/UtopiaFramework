package de.utopiamc.framework.common.service.impl;

import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.PlayerHandler;

import java.util.Date;
import java.util.UUID;

public abstract class CommonPlayerHandler implements PlayerHandler {

    protected final FrameworkPlayerService frameworkPlayerService;

    protected CommonPlayerHandler(FrameworkPlayerService frameworkPlayerService) {
        this.frameworkPlayerService = frameworkPlayerService;
    }

    protected void loginFrameworkPlayerData(UUID uuid, String name, Date firstLogin) {
        frameworkPlayerService.cache(uuid, name, firstLogin);
    }

}
