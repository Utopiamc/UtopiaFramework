package de.utopiamc.framework.common.service.impl;

import com.google.common.reflect.TypeToken;
import de.utopiamc.framework.api.Assertions;
import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.model.Economy;
import de.utopiamc.framework.api.model.PlayerEconomy;
import de.utopiamc.framework.api.model.RequestBodyType;
import de.utopiamc.framework.api.model.RequestResponse;
import de.utopiamc.framework.api.service.EconomyService;
import de.utopiamc.framework.api.service.RequestService;
import de.utopiamc.framework.common.dto.CommonEconomy;
import de.utopiamc.framework.common.dto.CommonPlayerEconomy;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CommonEconomyService implements EconomyService {

    private final RequestService requestService;

    @Inject
    public CommonEconomyService(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    @NotNull
    @SuppressWarnings("UnstableApiUsage")
    public Economy[] getEconomies() {
        try {
            Type type = new TypeToken<List<CommonEconomy>>(){}.getType();
            List<CommonEconomy> execute = requestService.get("/economy")
                    .execute(type);
            return execute.toArray(Economy[]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Optional<Economy> getEconomy(@NotNull UUID id) {
        Assertions.assertNonNull(id, "Id should not be null.");
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
    }

    @NotNull
    @Override
    @SuppressWarnings("UnstableApiUsage")
    public Set<Economy> getEconomy(@NotNull String name) {
        Assertions.assertNonNull(name, "Name should not be null.");
        try {
            Type type = new TypeToken<Set<CommonEconomy>>(){}.getType();
            return requestService.get("/economy")
                    .queryParam("name", name)
                    .execute(type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Set<PlayerEconomy> getEconomies(@NotNull FrameworkPlayer holder) {
        Assertions.assertNonNull(holder, "Holder should not be null.");
        return getEconomies(holder.getUuid());
    }

    @NotNull
    @Override
    @SuppressWarnings("UnstableApiUsage")
    public Set<PlayerEconomy> getEconomies(@NotNull UUID holder) {
        Assertions.assertNonNull(holder, "Holder should not be null.");
        try {
            return requestService.get("/economy/wallet")
                    .queryParam("holder", holder.toString())
                    .execute(new TypeToken<Set<CommonPlayerEconomy>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Optional<PlayerEconomy> getEconomy(@NotNull UUID economy, @NotNull FrameworkPlayer holder) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");
        return getEconomy(economy, holder.getUuid());
    }

    @NotNull
    @Override
    public Optional<PlayerEconomy> getEconomy(@NotNull UUID economy, @NotNull UUID holder) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        try {
            RequestResponse response = requestService.get("/economy/wallet")
                    .queryParam("holder", holder.toString())
                    .queryParam("economy", economy.toString())
                    .response();
            if (response.statusCode() == 404)
                return Optional.empty();
            return Optional.of(response.body(PlayerEconomy.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Set<PlayerEconomy> getEconomy(@NotNull String economy, @NotNull FrameworkPlayer holder) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        return getEconomy(economy, holder.getUuid());
    }

    @NotNull
    @Override
    @SuppressWarnings("UnstableApiUsage")
    public Set<PlayerEconomy> getEconomy(@NotNull String economy, @NotNull UUID holder) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        try {
            return requestService.get("/economy/wallet")
                    .queryParam("holder", holder.toString())
                    .queryParam("economy-name", economy)
                    .execute(new TypeToken<Set<PlayerEconomy>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Optional<PlayerEconomy> getEconomy(@NotNull Economy economy, @NotNull FrameworkPlayer holder) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        return getEconomy(economy, holder.getUuid());
    }

    @NotNull
    @Override
    public Optional<PlayerEconomy> getEconomy(@NotNull Economy economy, @NotNull UUID holder) {
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        return getEconomy(economy.id(), holder);
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

        try {
            requestService.post("/economy/wallet/transaction")
                    .body(body, RequestBodyType.APPLICATION_JSON)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean decrease(Double amount, Economy economy, FrameworkPlayer holder) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount>=0, "Amount should be lower than 0. Use EconomyService#increase instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        return decrease(amount, economy.id(), holder.getUuid());
    }

    @Override
    public boolean decrease(Double amount, Economy economy, UUID holder) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount>=0, "Amount should be lower than 0. Use EconomyService#increase instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        return decrease(amount, economy.id(), holder);
    }

    @Override
    public boolean decrease(Double amount, UUID economy, FrameworkPlayer holder) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount>=0, "Amount should be lower than 0. Use EconomyService#increase instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        return decrease(amount, economy, holder.getUuid());
    }

    @Override
    public boolean decrease(Double amount, UUID economy, UUID holder) {
        Assertions.assertNonNull(amount, "Amount should not be null.");
        Assertions.assertFalse(amount>=0, "Amount should be lower than 0. Use EconomyService#increase instead.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(holder, "Holder should not be null.");

        Map<String, Object> body = new HashMap<>();
        body.put("holder", holder);
        body.put("economy", economy);
        body.put("action", "DECREASE");
        body.put("amount", amount);

        try {
            return requestService.post("/economy/wallet/transaction")
                    .body(body, RequestBodyType.APPLICATION_JSON)
                    .execute(boolean.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public boolean transfer(Double amount, Economy economy, FrameworkPlayer from, FrameworkPlayer to) {
        Assertions.assertNonNull(amount, "Value should not be null.");
        Assertions.assertFalse(amount<=0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(from, "Holder should not be null.");
        Assertions.assertNonNull(to, "Holder should not be null.");

        return transfer(amount, economy.id(), from.getUuid(), to.getUuid());
    }

    @Override
    public boolean transfer(Double amount, Economy economy, UUID from, UUID to) {
        Assertions.assertNonNull(amount, "Value should not be null.");
        Assertions.assertFalse(amount<=0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(from, "Holder should not be null.");
        Assertions.assertNonNull(to, "Holder should not be null.");

        return transfer(amount, economy.id(), from, to);
    }

    @Override
    public boolean transfer(Double amount, UUID economy, FrameworkPlayer from, FrameworkPlayer to) {
        Assertions.assertNonNull(amount, "Value should not be null.");
        Assertions.assertFalse(amount<=0, "Value not should be lower than 0.");
        Assertions.assertNonNull(economy, "Economy should not be null.");
        Assertions.assertNonNull(from, "Holder should not be null.");
        Assertions.assertNonNull(to, "Holder should not be null.");

        return transfer(amount, economy, from.getUuid(), to.getUuid());
    }

    @Override
    public boolean transfer(Double amount, UUID economy, UUID from, UUID to) {
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

        try {
            return requestService.post("/economy/wallet/transaction")
                    .body(body, RequestBodyType.APPLICATION_JSON)
                    .execute(boolean.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
