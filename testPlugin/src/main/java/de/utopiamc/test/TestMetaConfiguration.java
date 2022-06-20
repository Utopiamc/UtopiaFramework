package de.utopiamc.test;

import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfiguration;
import de.utopiamc.framework.api.inject.components.Configuration;

@Configuration
public class TestMetaConfiguration implements UtopiaMetaConfiguration {

    @Override
    public UtopiaMetaConfig configure(UtopiaMetaConfig config) {

        config.primaryColor('6')
                .secondaryColor('e')
                .prefix("$p&lTest &8• $r");

        return config;
    }

}
