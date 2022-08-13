package de.utopiamc.framework.common.bootstrap.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.dropin.DropInService;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.common.dropin.impl.CachedDropInService;

public class FrameworkBootstrapModule extends AbstractModule {

    @Override
    protected void configure() {
        requestStaticInjection(ApplicationContext.class);

        bind(DropInService.class).to(CachedDropInService.class);
    }
}
