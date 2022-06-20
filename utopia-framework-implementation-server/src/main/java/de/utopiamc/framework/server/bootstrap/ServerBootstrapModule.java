package de.utopiamc.framework.server.bootstrap;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.common.service.DropInFileService;
import de.utopiamc.framework.server.service.ServerDropInFileService;

public class ServerBootstrapModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DropInFileService.class).to(ServerDropInFileService.class).asEagerSingleton();
    }

}
