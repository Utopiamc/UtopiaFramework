package de.utopiamc.framework.common.config;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfiguration;

import java.util.Collection;
import java.util.Set;

public class UtopiaMetaConfigProvider implements Provider<UtopiaMetaConfig> {

    private final Collection<UtopiaMetaConfiguration> utopiaMetaConfigurationClass;
    private UtopiaMetaConfig cachedConfig;

    @Inject
    public UtopiaMetaConfigProvider(Set<UtopiaMetaConfiguration> utopiaMetaConfigurationClass) {
        this.utopiaMetaConfigurationClass = utopiaMetaConfigurationClass;
    }

    @Override
    public UtopiaMetaConfig get() {
        if (cachedConfig == null) {
            UtopiaMetaConfig config = craftConfig();
            utopiaMetaConfigurationClass.forEach(c -> c.configure(config));
            cachedConfig = config;
        }
        return cachedConfig;
    }

    private UtopiaMetaConfig craftConfig() {
        UtopiaMetaConfig config = new UtopiaMetaConfigImpl();

        config.primaryColor('c')
                .secondaryColor('b')
                .textColor('7')
                .errorColor('c')
                .title("$p&lUtopia")
                .prefix("$p&lUtopia &8• $r");

        return config;
    }

}
