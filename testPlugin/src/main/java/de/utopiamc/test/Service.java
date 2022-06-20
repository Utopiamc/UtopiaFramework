package de.utopiamc.test;

import com.google.inject.Inject;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;

@de.utopiamc.framework.api.inject.components.Service
public class Service {

    @Inject
    public Service(UtopiaMetaConfig metaConfig) {
        System.out.println(metaConfig.getPrefix());
    }

}
