package de.utopiamc.framework.worker.economy;

import de.utopiamc.framework.worker.economy.dto.EconomyDto;
import de.utopiamc.framework.worker.economy.dto.EconomyTransactionDto;
import de.utopiamc.framework.worker.economy.dto.TransactionResponseDto;
import de.utopiamc.framework.worker.economy.dto.WalletHoldingsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/economy")
public class EconomyController {

    private final EconomyService economyService;

    //region Economies

    @GetMapping
    public ResponseEntity<EconomyDto[]> getAll() {
        return economyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EconomyDto> getById(@PathVariable("id") UUID id) {
        return economyService.getById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<EconomyDto[]> getByName(@PathVariable("name") String name) {
        return economyService.getByName(name);
    }

    //endregion

    //region Wallets

    @GetMapping("/wallet")
    public ResponseEntity<WalletHoldingsDto> getAllHoldings(@RequestParam("holder") UUID holder) {
        return economyService.getAllHoldings(holder);
    }

    @GetMapping("/wallet/{economy}")
    public ResponseEntity<WalletHoldingsDto> getHoldingById(@RequestParam("holder") UUID holder, @PathVariable("economy") UUID economy) {
        return economyService.getHoldingById(holder, economy);
    }

    @PostMapping("/wallet/transaction")
    public ResponseEntity<TransactionResponseDto> createTransaction(@Valid @RequestBody EconomyTransactionDto transaction) {
        return economyService.createTransaction(transaction);
    }

    //endregion

}
