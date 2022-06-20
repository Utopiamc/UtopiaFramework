package de.utopiamc.framework.api.config;

public interface UtopiaConfigurationClass<T extends UtopiaConfiguration> {

    T configure(T config);

}
