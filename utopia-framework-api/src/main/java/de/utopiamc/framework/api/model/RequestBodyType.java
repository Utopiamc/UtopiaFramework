package de.utopiamc.framework.api.model;

public enum RequestBodyType {
    APPLICATION_JSON("application/json");

    final String string;

    RequestBodyType(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
