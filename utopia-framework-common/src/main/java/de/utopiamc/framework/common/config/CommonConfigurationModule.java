package de.utopiamc.framework.common.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfiguration;

public class CommonConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UtopiaMetaConfig.class).toProvider(UtopiaMetaConfigProvider.class);

        Multibinder<UtopiaMetaConfiguration> mBinder = Multibinder.newSetBinder(binder(), UtopiaMetaConfiguration.class);
        mBinder.addBinding()
                .toInstance(config -> {});
    }
}
