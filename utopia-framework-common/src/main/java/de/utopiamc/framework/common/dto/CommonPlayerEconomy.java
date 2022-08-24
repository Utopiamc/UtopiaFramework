package de.utopiamc.framework.common.dto;

import de.utopiamc.framework.api.model.Economy;
import de.utopiamc.framework.api.model.PlayerEconomy;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class CommonPlayerEconomy implements PlayerEconomy {

    private final Economy economy;
    private final UUID holder;
    private final Double value;

    @Override
    public Economy getEconomy() {
        return economy;
    }

    @Override
    public UUID holder() {
        return holder;
    }

    @Override
    public Double getValue() {
        return value;
    }

}
