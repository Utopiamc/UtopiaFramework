package de.utopiamc.framework.api.config.request;

import de.utopiamc.framework.api.config.UtopiaConfiguration;

public interface RequestConfig extends UtopiaConfiguration {

    RequestConfig host(String host);
    RequestConfig port(Integer port);

    String getHost();
    Integer getPort();

}
