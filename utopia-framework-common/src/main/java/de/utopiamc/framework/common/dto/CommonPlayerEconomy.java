package de.utopiamc.framework.common.dto;

import de.utopiamc.framework.api.model.Economy;
import de.utopiamc.framework.api.model.PlayerEconomy;

import java.util.UUID;

public class CommonPlayerEconomy implements PlayerEconomy {

    @Override
    public Economy getEconomy() {
        return null;
    }

    @Override
    public UUID holder() {
        return null;
    }

    @Override
    public Double getValue() {
        return null;
    }

}
