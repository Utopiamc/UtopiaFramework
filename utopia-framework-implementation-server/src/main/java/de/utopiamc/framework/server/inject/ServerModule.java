package de.utopiamc.framework.server.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.common.service.EventConverterService;
import de.utopiamc.framework.server.service.ServerEventConverterService;
import de.utopiamc.framework.server.service.ServerFrameworkPlayerService;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EventConverterService.class).to(ServerEventConverterService.class);
        bind(FrameworkPlayerService.class).to(ServerFrameworkPlayerService.class);
    }
}
