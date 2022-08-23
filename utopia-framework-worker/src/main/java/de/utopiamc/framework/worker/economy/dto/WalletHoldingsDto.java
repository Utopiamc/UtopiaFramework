package de.utopiamc.framework.worker.economy.dto;

import lombok.Data;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
public class WalletHoldingsDto {

    private final UUID wallet;
    private final UUID holder;

    private final Map<UUID, EconomyDto> economies;
    private final Set<EconomyHoldingDto> holdings;

}
