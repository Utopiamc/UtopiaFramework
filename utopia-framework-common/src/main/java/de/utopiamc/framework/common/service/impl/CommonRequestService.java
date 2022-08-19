package de.utopiamc.framework.common.service.impl;

import de.utopiamc.framework.api.config.request.RequestConfig;
import de.utopiamc.framework.api.model.RequestBuilder;
import de.utopiamc.framework.api.service.RequestService;
import de.utopiamc.framework.common.models.CommonRequestBuilder;
import okhttp3.OkHttpClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommonRequestService implements RequestService {

    private final OkHttpClient client;
    private final RequestConfig config;

    @Inject
    public CommonRequestService(RequestConfig config) {
        this.client = new OkHttpClient();
        this.config = config;
    }


    @Override
    public RequestBuilder get(String path) {
        return new CommonRequestBuilder("get", path, client, config);
    }

    @Override
    public RequestBuilder post(String path) {
        return new CommonRequestBuilder("post", path, client, config);
    }

    @Override
    public RequestBuilder delete(String path) {
        return new CommonRequestBuilder("delete", path, client, config);
    }

    @Override
    public RequestBuilder patch(String path) {
        return new CommonRequestBuilder("path", path, client, config);
    }

    @Override
    public RequestBuilder put(String path) {
        return new CommonRequestBuilder("put", path, client, config);
    }

    @Override
    public RequestBuilder head(String path) {
        return new CommonRequestBuilder("head", path, client, config);
    }
}
