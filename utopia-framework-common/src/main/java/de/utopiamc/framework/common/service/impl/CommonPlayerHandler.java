package de.utopiamc.framework.common.service.impl;

import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.PlayerHandler;
import de.utopiamc.framework.common.messaging.StompHandler;

import java.util.Date;
import java.util.UUID;

public abstract class CommonPlayerHandler implements PlayerHandler {

    protected final FrameworkPlayerService frameworkPlayerService;
    protected final StompHandler stompHandler;

    protected CommonPlayerHandler(FrameworkPlayerService frameworkPlayerService, StompHandler stompHandler) {
        this.frameworkPlayerService = frameworkPlayerService;
        this.stompHandler = stompHandler;
    }

    protected void loginFrameworkPlayerData(UUID uuid, String name, Date firstLogin) {
        System.out.println(uuid);
        System.out.println(name);
        frameworkPlayerService.cache(uuid, name, firstLogin);

        stompHandler.subscribe(uuid);
    }

}
