package de.utopiamc.framework.common.config;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import de.utopiamc.framework.api.config.UtopiaConfigurationClass;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfiguration;
import de.utopiamc.framework.api.config.request.RequestConfig;
import de.utopiamc.framework.api.config.request.RequestConfiguration;

public class CommonConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UtopiaMetaConfig.class).toProvider(UtopiaMetaConfigProvider.class);
        bind(RequestConfig.class).toProvider(RequestConfigProvider.class);

        addDefaultMultiBinderBinder(UtopiaMetaConfiguration.class, config -> {});
        addDefaultMultiBinderBinder(RequestConfiguration.class, config -> {});
    }

    private <T extends UtopiaConfigurationClass<?>> void addDefaultMultiBinderBinder(Class<T> binderClass, T t) {
        Multibinder.newSetBinder(binder(), binderClass).addBinding()
                .toInstance(t);
    }
}
