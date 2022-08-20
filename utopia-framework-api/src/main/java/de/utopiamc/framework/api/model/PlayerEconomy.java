package de.utopiamc.framework.api.model;

import java.util.UUID;

public interface PlayerEconomy {

    Economy getEconomy();

    UUID holder();

    Double getValue();

}
