package de.utopiamc.framework.common.config;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.utopiamc.framework.api.config.request.RequestConfig;
import de.utopiamc.framework.api.config.request.RequestConfiguration;

import java.util.Collection;
import java.util.Set;

public class RequestConfigProvider implements Provider<RequestConfig> {

    private final Collection<RequestConfiguration> requestConfigurations;
    private RequestConfig cachedConfig;

    @Inject
    public RequestConfigProvider(Set<RequestConfiguration> requestConfigurations) {
        this.requestConfigurations = requestConfigurations;
    }

    @Override
    public RequestConfig get() {
        if (cachedConfig == null) {
            RequestConfig config = new RequestConfigImpl("balance.utopiamc.de", 8080);
            requestConfigurations.forEach(c -> c.configure(config));
            cachedConfig = config;
        }
        return cachedConfig;
    }

}
