package de.utopiamc.framework.api.model;

import java.io.IOException;
import java.lang.reflect.Type;

public interface RequestBuilder {

    RequestBuilder queryParam(String key, String value);

    RequestBuilder body(Object obj, RequestBodyType type);

    <T> T execute(Type typeOfT) throws IOException;

    String execute() throws IOException;

    RequestResponse response() throws IOException;
}
