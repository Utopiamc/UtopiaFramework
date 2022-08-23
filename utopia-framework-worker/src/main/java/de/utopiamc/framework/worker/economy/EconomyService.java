package de.utopiamc.framework.worker.economy;

import de.utopiamc.framework.worker.economy.dto.EconomyDto;
import de.utopiamc.framework.worker.economy.dto.EconomyTransactionDto;
import de.utopiamc.framework.worker.economy.dto.TransactionResponseDto;
import de.utopiamc.framework.worker.economy.dto.WalletHoldingsDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface EconomyService {

    ResponseEntity<EconomyDto[]> getAll();

    ResponseEntity<EconomyDto> getById(UUID id);

    ResponseEntity<EconomyDto[]> getByName(String name);

    ResponseEntity<WalletHoldingsDto> getAllHoldings(UUID holder);

    ResponseEntity<WalletHoldingsDto> getHoldingById(UUID holder, UUID economy);

    ResponseEntity<TransactionResponseDto> createTransaction(EconomyTransactionDto transaction);

}
