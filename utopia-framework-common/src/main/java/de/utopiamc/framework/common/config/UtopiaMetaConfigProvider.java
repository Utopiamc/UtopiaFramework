package de.utopiamc.framework.common.config;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfiguration;

public class UtopiaMetaConfigProvider implements Provider<UtopiaMetaConfig> {

    private final UtopiaMetaConfiguration utopiaMetaConfigurationClass;
    private UtopiaMetaConfig cachedConfig;

    @Inject
    public UtopiaMetaConfigProvider(UtopiaMetaConfiguration utopiaMetaConfigurationClass) {
        this.utopiaMetaConfigurationClass = utopiaMetaConfigurationClass;
    }

    @Override
    public UtopiaMetaConfig get() {
        if (cachedConfig == null)
            cachedConfig = utopiaMetaConfigurationClass.configure(craftConfig());
        return cachedConfig;
    }

    private UtopiaMetaConfig craftConfig() {
        UtopiaMetaConfig config = new UtopiaMetaConfigImpl();

        config.primaryColor('c')
                .secondaryColor('b')
                .textColor('7')
                .errorColor('c')
                .prefix("$p&lUtopia &8• $r");

        return config;
    }

}
