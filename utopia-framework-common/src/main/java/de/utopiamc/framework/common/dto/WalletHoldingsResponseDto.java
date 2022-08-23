package de.utopiamc.framework.common.dto;

import lombok.Value;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Value
public class WalletHoldingsResponseDto {

    UUID wallet;
    UUID holder;

    Map<UUID, CommonEconomy> economies;

    Set<Holding> holdings;

    @Value
    public static class Holding {

        UUID economy;
        Double value;

    }

}
