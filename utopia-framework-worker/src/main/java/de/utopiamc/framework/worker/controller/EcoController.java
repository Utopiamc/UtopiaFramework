package de.utopiamc.framework.worker.controller;

import de.utopiamc.framework.worker.dto.CurrencyDto;
import de.utopiamc.framework.worker.dto.TransactionDto;
import de.utopiamc.framework.worker.service.EconomyService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Data
@RestController
@RequestMapping("/eonomy")
public class EcoController {

    private final EconomyService economyService;

    @GetMapping
    public ResponseEntity<CurrencyDto[]> getAll() {
        return null;
    }

    @PostMapping("/transaction")
    public void transaction(@RequestParam UUID currency, @RequestBody TransactionDto transactionDto) {

    }


}
