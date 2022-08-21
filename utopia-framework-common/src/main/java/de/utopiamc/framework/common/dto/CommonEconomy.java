package de.utopiamc.framework.common.dto;

import de.utopiamc.framework.api.model.Economy;

import java.util.UUID;

public class CommonEconomy implements Economy {

    private UUID id;
    private String name;
    private String symbol;
    private Short value;

    @Override
    public UUID id() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getSymbol() {
        return null;
    }

    @Override
    public Short getGlobalValue() {
        return null;
    }
}
