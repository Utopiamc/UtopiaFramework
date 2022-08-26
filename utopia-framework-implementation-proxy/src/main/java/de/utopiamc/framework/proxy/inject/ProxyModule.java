package de.utopiamc.framework.proxy.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.service.AsyncService;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.PlayerHandler;
import de.utopiamc.framework.common.service.EventConverterService;
import de.utopiamc.framework.proxy.service.ProxyAsyncService;
import de.utopiamc.framework.proxy.service.ProxyEventConverterService;
import de.utopiamc.framework.proxy.service.ProxyFrameworkPlayerService;
import de.utopiamc.framework.proxy.service.ProxyPlayerHandler;

public class ProxyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AsyncService.class).to(ProxyAsyncService.class);
        bind(PlayerHandler.class).to(ProxyPlayerHandler.class);
        bind(EventConverterService.class).to(ProxyEventConverterService.class);
        bind(FrameworkPlayerService.class).to(ProxyFrameworkPlayerService.class);
    }

}
