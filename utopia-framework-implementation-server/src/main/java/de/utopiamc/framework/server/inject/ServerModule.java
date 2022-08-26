package de.utopiamc.framework.server.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.service.AsyncService;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.PlayerHandler;
import de.utopiamc.framework.common.service.EventConverterService;
import de.utopiamc.framework.server.service.ServerAsyncService;
import de.utopiamc.framework.server.service.ServerEventConverterService;
import de.utopiamc.framework.server.service.ServerFrameworkPlayerService;
import de.utopiamc.framework.server.service.ServerPlayerHandler;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventConverterService.class).to(ServerEventConverterService.class);
        bind(FrameworkPlayerService.class).to(ServerFrameworkPlayerService.class);
        bind(AsyncService.class).to(ServerAsyncService.class);
        bind(PlayerHandler.class).to(ServerPlayerHandler.class);
    }
}
