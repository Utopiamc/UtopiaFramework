package de.utopiamc.framework.common.service.impl;

import com.google.common.reflect.TypeToken;
import de.utopiamc.framework.api.Assertions;
import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.model.Economy;
import de.utopiamc.framework.api.model.PlayerEconomy;
import de.utopiamc.framework.api.model.RequestBodyType;
import de.utopiamc.framework.api.model.RequestResponse;
import de.utopiamc.framework.api.service.AsyncService;
import de.utopiamc.framework.api.service.EconomyService;
import de.utopiamc.framework.api.service.RequestService;
import de.utopiamc.framework.common.dto.CommonEconomy;
import de.utopiamc.framework.common.dto.CommonPlayerEconomy;
import de.utopiamc.framework.common.dto.TransactionResponseDto;
import de.utopiamc.framework.common.dto.WalletHoldingsResponseDto;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CommonEconomyService implements EconomyService {

    private final RequestService requestService;
    private final AsyncService asyncService;

    @Inject
    public CommonEconomyService(RequestService requestService, AsyncService asyncService) {
        this.requestService = requestService;
        this.asyncService = asyncService;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void getEconomies(Consumer<Economy[]> callback) {
        asyncService.doAsync(() -> {
            try {
                Type type = new TypeToken<List<CommonEconomy>>(){}.getType();
                List<CommonEconomy> execute = requestService.get("/economy")
                        .execute(type);
                return execute.toArray(Economy[]::new);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, callback);
    }

    @Override
    public void getEconomy(@NotNull UUID id, Consumer<Optional<Economy>> callback) {
        Assertions.assertNonNull(id, "Id should not be null.");
        asyncService.doAsync(() -> {
            try {
                RequestResponse response = requestService.get("/economy")
                        .queryParam("id", id.toString())
                        .response();
                int i = response.statusCode();
                if (i == 404)
                    return Optional.empty();
                return Optional.of(response.body(CommonEconomy.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, callback);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void getEconomy(@NotNull String name, Consumer<Set<Economy>> callback) {
        Assertions.assertNonNull(name, "Name should not be null.");
        asyncService.doAsync(() -> {
            try {
                Type type = new TypeToken<Set<CommonEconomy>>(){}.getType();
                return requestService.get("/economy")
                        .queryParam("name", name)
                        .execute(type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, callback);
    }

    @Override
    public void getEconomies(@NotNull FrameworkPlayer holder, Consumer<Set<PlayerEconomy>> callback) {
        Assertions.assertNonNull(holder, "Holder should not be null.");

        getEconomies(holder.getUuid(), callback);
    }

    @Override
    public void getEconomies(@NotNull UUID holder, Consumer<Set<PlayerEconomy>> callback) {
        Assertions.assertNonNull(holder, "Holder should not be null.");

        asyncService.doAsync(() -> {
            try {
                WalletHoldingsResponseDto response = requestService.get("/economy/wallet")
                        .queryParam("holder", holder.toString())
                        .execute(WalletHoldingsResponseDto.class);

                return response.getHoldings().stream()
                        .map(holding -> new CommonPlayerEconomy(response.getEconomies().get(holding.getEconomy()),
                                response.getHolder(),
                                holding.getValue()))
                        .collect(Collectors.toSet());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, callback);
    }

    @Override
    public void getEconomy(@NotNull UUID economy, @NotNull FrameworkPlayer holder, Consumer<Optional<PlayerEconomy>> callback) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        getEconomy(economy, holder.getUuid(), callback);
    }

    @Override
    public void getEconomy(@NotNull UUID economy, @NotNull UUID holder, Consumer<Optional<PlayerEconomy>> callback) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        asyncService.doAsync(() -> {
            try {
                RequestResponse response = requestService.get("/economy/wallet/" + economy)
                        .queryParam("holder", holder.toString())
                        .response();
                if (response.statusCode() == 404)
                    return Optional.empty();
                WalletHoldingsResponseDto sholding = response.body(WalletHoldingsResponseDto.class);
                WalletHoldingsResponseDto.Holding holding = sholding.getHoldings().stream().findFirst().get();
                return Optional.of(new CommonPlayerEconomy(sholding.getEconomies().get(holding.getEconomy()), holder, holding.getValue()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, callback);
    }

    @Override
    public void getEconomy(@NotNull Economy economy, @NotNull FrameworkPlayer holder, Consumer<Optional<PlayerEconomy>> callback) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        getEconomy(economy, holder.getUuid(), callback);
    }

    @Override
    public void getEconomy(@NotNull Economy economy, @NotNull UUID holder, Consumer<Optional<PlayerEconomy>> callback) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        getEconomy(economy.id(), holder, callback);
    }

    @Override
    public void increase(Double amount, Economy economy, FrameworkPlayer holder) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount<=0, "Amount should be higher than 0. Use EconomyService#decrease instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        increase(amount, economy.id(), holder.getUuid());
    }

    @Override
    public void increase(Double amount, Economy economy, UUID holder) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount<=0, "Amount should be higher than 0. Use EconomyService#decrease instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        increase(amount, economy.id(), holder);
    }

    @Override
    public void increase(Double amount, UUID economy, FrameworkPlayer holder) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount<=0, "Amount should be higher than 0. Use EconomyService#decrease instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        increase(amount, economy, holder.getUuid());
    }

    @Override
    public void increase(Double amount, UUID economy, UUID holder) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount<=0, "Amount should be higher than 0. Use EconomyService#decrease instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        Map<String, Object> body = new HashMap<>();
        body.put("holder", holder);
        body.put("economy", economy);
        body.put("action", "INCREASE");
        body.put("amount", amount);

        asyncService.doAsync(() -> {
            try {
                requestService.post("/economy/wallet/transaction")
                        .body(body, RequestBodyType.APPLICATION_JSON)
                        .execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, (n) -> {});
    }

    @Override
    public void decrease(Double amount, Economy economy, FrameworkPlayer holder, Consumer<Boolean> callback) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount>=0, "Amount should be lower than 0. Use EconomyService#increase instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        decrease(amount, economy.id(), holder.getUuid(), callback);
    }

    @Override
    public void decrease(Double amount, Economy economy, UUID holder, Consumer<Boolean> callback) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount>=0, "Amount should be lower than 0. Use EconomyService#increase instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        decrease(amount, economy.id(), holder, callback);
    }

    @Override
    public void decrease(Double amount, UUID economy, FrameworkPlayer holder, Consumer<Boolean> callback) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount>=0, "Amount should be lower than 0. Use EconomyService#increase instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        decrease(amount, economy, holder.getUuid(), callback);
    }

    @Override
    public void decrease(Double amount, UUID economy, UUID holder, Consumer<Boolean> callback) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount>=0, "Amount should be lower than 0. Use EconomyService#increase instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        Map<String, Object> body = new HashMap<>();
        body.put("holder", holder);
        body.put("economy", economy);
        body.put("action", "DECREASE");
        body.put("amount", amount);

        asyncService.doAsync(() -> {
            try {
                return ((TransactionResponseDto)requestService.post("/economy/wallet/transaction")
                        .body(body, RequestBodyType.APPLICATION_JSON)
                        .execute(TransactionResponseDto.class))
                        .isSuccess();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, callback);
    }

    @Override
    public void set(Double value, Economy economy, FrameworkPlayer holder) {
        Assertions.assertNonNull(value, "Value should not be null.");
        Assertions.assertFalse(value<0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        set(value, economy.id(), holder.getUuid());
    }

    @Override
    public void set(Double value, Economy economy, UUID holder) {
        Assertions.assertNonNull(value, "Value should not be null.");
        Assertions.assertFalse(value<0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        set(value, economy.id(), holder);
    }

    @Override
    public void set(Double value, UUID economy, FrameworkPlayer holder) {
        Assertions.assertNonNull(value, "Value should not be null.");
        Assertions.assertFalse(value<0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        set(value, economy, holder.getUuid());
    }

    @Override
    public void set(Double value, UUID economy, UUID holder) {
        Assertions.assertNonNull(value, "Value should not be null.");
        Assertions.assertFalse(value<0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        Map<String, Object> body = new HashMap<>();
        body.put("holder", holder);
        body.put("economy", economy);
        body.put("action", "SET");
        body.put("value", value);

        try {
            requestService.post("/economy/wallet/transaction")
                    .body(body, RequestBodyType.APPLICATION_JSON)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void transfer(Double amount, Economy economy, FrameworkPlayer from, FrameworkPlayer to, Consumer<Boolean> callback) {
        Assertions.assertNonNull(amount, "Value should not be null.");
        Assertions.assertFalse(amount<=0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(from, "Holder should not be null.");
        Assertions.assertNonNull(to, "Holder should not be null.");

        transfer(amount, economy.id(), from.getUuid(), to.getUuid(), callback);
    }

    @Override
    public void transfer(Double amount, Economy economy, UUID from, UUID to, Consumer<Boolean> callback) {
        Assertions.assertNonNull(amount, "Value should not be null.");
        Assertions.assertFalse(amount<=0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(from, "Holder should not be null.");
        Assertions.assertNonNull(to, "Holder should not be null.");

        transfer(amount, economy.id(), from, to, callback);
    }

    @Override
    public void transfer(Double amount, UUID economy, FrameworkPlayer from, FrameworkPlayer to, Consumer<Boolean> callback) {
        Assertions.assertNonNull(amount, "Value should not be null.");
        Assertions.assertFalse(amount<=0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(from, "Holder should not be null.");
        Assertions.assertNonNull(to, "Holder should not be null.");

        transfer(amount, economy, from.getUuid(), to.getUuid(), callback);
    }

    @Override
    public void transfer(Double amount, UUID economy, UUID from, UUID to, Consumer<Boolean> callback) {
        Assertions.assertNonNull(amount, "Value should not be null.");
        Assertions.assertFalse(amount<=0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(from, "Holder should not be null.");
        Assertions.assertNonNull(to, "Holder should not be null.");

        Map<String, Object> body = new HashMap<>();
        body.put("holder", from);
        body.put("to", to);
        body.put("economy", economy);
        body.put("action", "TRANSFER");
        body.put("value", amount);

        asyncService.doAsync(() -> {
            try {
                return ((TransactionResponseDto)requestService.post("/economy/wallet/transaction")
                        .body(body, RequestBodyType.APPLICATION_JSON)
                        .execute(TransactionResponseDto.class))
                        .isSuccess();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, callback);
    }

}
