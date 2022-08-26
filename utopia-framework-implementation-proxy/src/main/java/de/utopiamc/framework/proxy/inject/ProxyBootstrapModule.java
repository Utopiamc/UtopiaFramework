package de.utopiamc.framework.proxy.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.common.service.DropInFileService;
import de.utopiamc.framework.proxy.service.ProxyDropInFileService;

public class ProxyBootstrapModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DropInFileService.class).to(ProxyDropInFileService.class).asEagerSingleton();
    }
}
