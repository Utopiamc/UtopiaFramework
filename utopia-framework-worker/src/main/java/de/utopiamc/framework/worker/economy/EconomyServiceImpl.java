package de.utopiamc.framework.worker.economy;

import de.utopiamc.framework.worker.economy.database.*;
import de.utopiamc.framework.worker.economy.dto.*;
import de.utopiamc.framework.worker.economy.packet.EconomyHoldingsUpdateCause;
import de.utopiamc.framework.worker.economy.packet.EconomyHoldingsUpdatePacket;
import de.utopiamc.framework.worker.event.EventPublishService;
import de.utopiamc.framework.worker.service.DtoService;
import lombok.RequiredArgsConstructor;
import org.neo4j.cypherdsl.core.utils.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EconomyServiceImpl implements EconomyService {

    private final EconomyRepository economyRepository;
    private final WalletRepository walletRepository;
    private final DtoService dtoService;

    private final EventPublishService publishService;
    @Override
    public ResponseEntity<EconomyDto[]> getAll() {

        return ResponseEntity.ok(economyRepository.findAll()
                .stream()
                .map(dtoService::toDto)
                .toArray(EconomyDto[]::new));
    }

    @Override
    public ResponseEntity<EconomyDto> getById(UUID id) {
        return ResponseEntity.of(economyRepository
                .findById(id)
                .map(dtoService::toDto));
    }

    @Override
    public ResponseEntity<EconomyDto[]> getByName(String name) {
        return ResponseEntity.ok(economyRepository.findAllByName(name).stream()
                .map(dtoService::toDto)
                .toArray(EconomyDto[]::new));
    }

    @Override
    public ResponseEntity<WalletHoldingsDto> getAllHoldings(UUID holder) {
        try {
            WalletEntity wallet = walletRepository.getAllHoldings(holder).orElseThrow(NullPointerException::new);

            return getWalletHoldingsDtoResponseEntity(holder, wallet);
        }catch (NullPointerException ignored) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<WalletHoldingsDto> getHoldingById(UUID holder, UUID economy) {
        try {
            WalletEntity wallet = walletRepository.getHoldingByEconomy(holder, economy).orElseThrow(NullPointerException::new);

            return getWalletHoldingsDtoResponseEntity(holder, wallet);
        }catch (NullPointerException ignored) {
            return ResponseEntity.notFound().build();
        }
    }

    @NotNull
    private ResponseEntity<WalletHoldingsDto> getWalletHoldingsDtoResponseEntity(UUID holder, WalletEntity wallet) {
        Map<UUID, EconomyDto> economies = new HashMap<>();
        Set<EconomyHoldingDto> holdings = new HashSet<>();

        for (WalletHoldingRelationShip rel : wallet.getHoldings()) {
            EconomyDto economyDto = dtoService.toDto(rel.getEconomy());
            EconomyHoldingDto walletDto = dtoService.toDto(rel);

            economies.put(economyDto.getId(), economyDto);
            holdings.add(walletDto);
        }

        return ResponseEntity.ok(dtoService.toDto(wallet.getId(), holder, economies, holdings));
    }

    @Override
    public ResponseEntity<TransactionResponseDto> createTransaction(EconomyTransactionDto transaction) {
        try {
            WalletEntity wallet = ensureWalletExists(transaction.getHolder(), transaction.getEconomy());
            for (WalletHoldingRelationShip holding : wallet.getHoldings()) {
                if (holding.getEconomy().getId().equals(transaction.getEconomy()))
                    return switch (transaction.getAction()) {
                        case "INCREASE" -> increase(transaction, wallet, holding);
                        case "DECREASE" -> decrease(transaction, wallet, holding);
                        case "SET" -> set(transaction, wallet, holding);
                        case "TRANSFER" -> transfer(transaction, wallet, holding);
                        default -> throw new IllegalStateException();
                    };
            }
            return ResponseEntity.notFound().build();
        }catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private WalletEntity ensureWalletExists(UUID holder, UUID economy) {
        Optional<WalletEntity> holdingByEconomy = walletRepository.getHoldingByEconomy(holder, economy);
        if (holdingByEconomy.isEmpty()) {
            EconomyEntity economyEntity = economyRepository.findById(economy).orElseThrow(() -> new NotFoundException("Economy not found."));
            WalletEntity wallet = new WalletEntity();
            WalletHoldingRelationShip walletHoldingRelationShip = new WalletHoldingRelationShip();
            walletHoldingRelationShip.setEconomy(economyEntity);
            walletHoldingRelationShip.setValue(0.0);
            wallet.setHoldings(List.of(walletHoldingRelationShip));
            walletRepository.save(wallet);
            return wallet;
        }
        return holdingByEconomy.get();
    }

    private ResponseEntity<TransactionResponseDto> transfer(EconomyTransactionDto transaction, WalletEntity wallet, WalletHoldingRelationShip holding) {
        Assertions.notNull(transaction.getAmount(), "Amount should not be null.");
        Assertions.isTrue(!(transaction.getAmount()<=0), "Amount should be positive.");
        Assertions.notNull(transaction.getTo(), "Receiver aka. 'to' should not be null.");

        WalletEntity receiverWallet = ensureWalletExists(transaction.getTo(), transaction.getEconomy());
        WalletHoldingRelationShip receiverHolding = null;
        for (WalletHoldingRelationShip holding1 : receiverWallet.getHoldings()) {
            if (holding1.getEconomy().getId().equals(transaction.getEconomy()))
                receiverHolding = holding1;
        }
        if (receiverHolding==null)
            throw new IllegalStateException();

        if (holding.getValue() < transaction.getAmount())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(TransactionResponseDto.of(false, holding.getValue()));

        holding.setValue(holding.getValue() - transaction.getAmount());
        receiverHolding.setValue(receiverHolding.getValue() + transaction.getAmount());

        walletRepository.save(wallet);
        walletRepository.save(receiverWallet);

        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponseDto.of(true, holding.getValue()));
    }

    private ResponseEntity<TransactionResponseDto> set(EconomyTransactionDto transaction, WalletEntity wallet, WalletHoldingRelationShip holding) {
        Assertions.notNull(transaction.getValue(), "Amount should not be null.");
        Assertions.isTrue(!(transaction.getValue()<=0), "Amount should be positive.");

        Double prev = holding.getValue();
        holding.setValue(transaction.getValue());
        walletRepository.save(wallet);

        publishUpdate(transaction.getHolder(),
                EconomyHoldingsUpdateCause.SET,
                holding.getEconomy(),
                prev,
                holding.getValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponseDto.of(true, holding.getValue()));
    }

    private ResponseEntity<TransactionResponseDto> decrease(EconomyTransactionDto transaction, WalletEntity wallet, WalletHoldingRelationShip holding) {
        Assertions.notNull(transaction.getAmount(), "Amount should not be null.");
        Assertions.isTrue(!(transaction.getAmount()<=0), "Amount should be positive.");

        if (holding.getValue()-transaction.getAmount() <= 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(TransactionResponseDto.of(false, holding.getValue()));

        holding.setValue(holding.getValue() - transaction.getAmount());
        walletRepository.save(wallet);

        publishUpdate(transaction.getHolder(),
                EconomyHoldingsUpdateCause.DECREASE,
                holding.getEconomy(),
                holding.getValue() + transaction.getAmount(),
                holding.getValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponseDto.of(true, holding.getValue()));
    }

    private ResponseEntity<TransactionResponseDto> increase(EconomyTransactionDto transaction, WalletEntity wallet, WalletHoldingRelationShip holding) {
        Assertions.notNull(transaction.getAmount(), "Amount should not be null.");
        Assertions.isTrue(!(transaction.getAmount()<=0), "Amount should be positive.");

        holding.setValue(holding.getValue() + transaction.getAmount());
        walletRepository.save(wallet);

        publishUpdate(transaction.getHolder(),
                EconomyHoldingsUpdateCause.INCREASE,
                holding.getEconomy(),
                holding.getValue() - transaction.getAmount(),
                holding.getValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionResponseDto.of(true, holding.getValue()));
    }

    private void publishUpdate(UUID holder, EconomyHoldingsUpdateCause cause, EconomyEntity economy, Double prevValue, Double newValue) {
        publishService.publishPlayer(holder, EconomyHoldingsUpdatePacket.builder()
                .holder(holder)
                .cause(cause)
                .economy(dtoService.toDto(economy))
                .previousValue(prevValue)
                .newValue(newValue)
                .build());
    }

}
