package de.utopiamc.framework.worker.service;

import de.utopiamc.framework.worker.dto.CurrencyDto;
import de.utopiamc.framework.worker.dto.PlayerDto;
import de.utopiamc.framework.worker.dto.StatsDto;
import de.utopiamc.framework.worker.entity.Currency;
import de.utopiamc.framework.worker.entity.Player;
import de.utopiamc.framework.worker.entity.Stats;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public CurrencyDto toDto(Currency currency) {
        return new CurrencyDto(currency.getUuid(),
                currency.getDisplay(),
                currency.getName(),
                currency.getValue());
    }

    public WalletDto toDto(Wallet wallet) {
        Map<CurrencyDto, Long> currencies = new HashMap<>();
        for (WalletCurrencyRelation currency : wallet.getCurrencies()) {
            currencies.put(toDto(currency.getCurrency()), currency.getAmount());
        }

        return new WalletDto(
                wallet.getId(),
                currencies,
                wallet.getContributors().stream().map((c) -> toDto(c.getPlayer())).collect(Collectors.toList())
        );
    }
}
