package de.utopiamc.framework.api.service;

import de.utopiamc.framework.api.model.RequestBuilder;

public interface RequestService {

    RequestBuilder get(String path);

    RequestBuilder post(String path);

    RequestBuilder delete(String path);

    RequestBuilder patch(String path);

    RequestBuilder put(String path);

    RequestBuilder head(String path);

}
