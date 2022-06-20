package de.utopiamc.framework.api.config.meta;

import com.google.inject.ImplementedBy;
import de.utopiamc.framework.api.config.UtopiaConfigurationClass;

@ImplementedBy(DefaultMetaConfiguration.class)
public interface UtopiaMetaConfiguration extends UtopiaConfigurationClass<UtopiaMetaConfig> {
}
