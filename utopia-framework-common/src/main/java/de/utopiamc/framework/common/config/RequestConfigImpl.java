package de.utopiamc.framework.common.config;

import de.utopiamc.framework.api.config.request.RequestConfig;

public class RequestConfigImpl implements RequestConfig {

    private String host;
    private Integer port;

    public RequestConfigImpl(String s, int i) {
        this.host = s;
        this.port = i;
    }

    @Override
    public RequestConfig host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public RequestConfig port(Integer port) {
        this.port = port;
        return this;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public Integer getPort() {
        return port;
    }
}
