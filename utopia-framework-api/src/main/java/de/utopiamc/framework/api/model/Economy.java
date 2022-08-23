package de.utopiamc.framework.api.model;

import java.util.UUID;

public interface Economy {

    UUID id();

    String getName();

    String getSymbol();

    Short getGlobalValue();

}
