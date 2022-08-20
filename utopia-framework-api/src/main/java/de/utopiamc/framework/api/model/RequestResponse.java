package de.utopiamc.framework.api.model;

public interface RequestResponse {

    int statusCode();

    <T> T body(Class<T> entity);

    String body();

}
