package de.utopiamc.framework.worker.service;

import de.utopiamc.framework.worker.dto.*;
import de.utopiamc.framework.worker.economy.dto.EconomyDto;
import de.utopiamc.framework.worker.economy.dto.EconomyHoldingDto;
import de.utopiamc.framework.worker.economy.dto.WalletHoldingsDto;
import de.utopiamc.framework.worker.economy.database.EconomyEntity;
import de.utopiamc.framework.worker.economy.database.WalletHoldingRelationShip;
import de.utopiamc.framework.worker.entity.Currency;
import de.utopiamc.framework.worker.entity.Player;
import de.utopiamc.framework.worker.entity.Stats;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class DtoService {

    public StatsDto toDto(Stats stats) {
        return new StatsDto(stats.getRelation().getGame(),
                toDto(stats.getRelation().getPlayer()),
                stats.getProperties());
    }

    public PlayerDto toDto(Player player) {
        return new PlayerDto(player.getUuid(), player.getName(), player.getFirstJoined());
    }

    public PlayerDetailsDto toDto(Player player, BanDto banDto) {
        return new PlayerDetailsDto(toDto(player), banDto);
    }

    public CurrencyDto toDto(Currency currency) {
        return new CurrencyDto(currency.getUuid(),
                currency.getDisplay(),
                currency.getName(),
                currency.getValue());
    }

    public EconomyDto toDto(EconomyEntity economyEntity) {
        return EconomyDto.of(economyEntity.getId(), economyEntity.getName(), economyEntity.getSymbol(), economyEntity.getValue());
    }

    public WalletHoldingsDto toDto(UUID wallet, UUID holder, Map<UUID, EconomyDto> economies, Set<EconomyHoldingDto> holdings) {
        return new WalletHoldingsDto(wallet, holder, economies, holdings);
    }

    public EconomyHoldingDto toDto(WalletHoldingRelationShip rel) {
        return EconomyHoldingDto.of(rel.getEconomy().getId(), rel.getValue());
    }
}
