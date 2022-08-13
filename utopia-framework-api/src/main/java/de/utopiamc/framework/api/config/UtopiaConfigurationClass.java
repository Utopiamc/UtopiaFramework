package de.utopiamc.framework.api.config;

public interface UtopiaConfigurationClass<T extends UtopiaConfiguration> {

    void configure(T config);

}
