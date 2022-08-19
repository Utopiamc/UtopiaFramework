package de.utopiamc.framework.api.model;

import java.io.IOException;

public interface RequestBuilder {

    RequestBuilder queryParam(String key, String value);

    RequestBuilder body(Object obj, RequestBodyType type);

    <T> T execute(Class<T> entity) throws IOException;

    String execute() throws IOException;

}
