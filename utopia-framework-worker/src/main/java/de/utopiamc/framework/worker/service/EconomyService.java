package de.utopiamc.framework.worker.service;

import de.utopiamc.framework.worker.dto.CurrencyDto;
import de.utopiamc.framework.worker.repository.EconomyRepository;
import de.utopiamc.framework.worker.repository.PlayerRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Data
@Service
public class EconomyService {

    private final EconomyRepository economyRepository;
    private final WalletRepository walletRepository;
    private final PlayerRepository playerRepository;
    private final DtoService dtoService;

    public ResponseEntity<CurrencyDto[]> getAll() {
        List<CurrencyDto> all = economyRepository.findAll().stream().map(dtoService::toDto).toList();

        return ResponseEntity.ok(all.toArray(CurrencyDto[]::new));
    }

    public ResponseEntity<WalletDto[]> getWallets(UUID player) {
        return ResponseEntity.ok(walletRepository.getWalletsByContributor(player).stream().map(dtoService::toDto).toArray(WalletDto[]::new));
    }

}
