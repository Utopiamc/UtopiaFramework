package de.utopiamc.framework.common.config;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;

public class CommonConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UtopiaMetaConfig.class).toProvider(UtopiaMetaConfigProvider.class);
    }
}
