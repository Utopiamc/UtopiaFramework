package de.utopiamc.framework.api.service;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.model.Economy;
import de.utopiamc.framework.api.model.PlayerEconomy;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public interface EconomyService {

    // Getting economy types

    void getEconomies(Consumer<Economy[]> callback);

    void getEconomy(@NotNull UUID id, Consumer<Optional<Economy>> callback);

    void getEconomy(@NotNull String name, Consumer<Set<Economy>> callback);

    // Getting player savings

    void getEconomies(@NotNull FrameworkPlayer holder, Consumer<Set<PlayerEconomy>> callback);

    void getEconomies(@NotNull UUID holder, Consumer<Set<PlayerEconomy>> callback);

    void getEconomy(@NotNull UUID economy, @NotNull FrameworkPlayer holder, Consumer<Optional<PlayerEconomy>> callback);

    void getEconomy(@NotNull UUID economy, @NotNull UUID holder, Consumer<Optional<PlayerEconomy>> callback);

    void getEconomy(@NotNull Economy economy, @NotNull FrameworkPlayer holder, Consumer<Optional<PlayerEconomy>> callback);

    void getEconomy(@NotNull Economy economy, @NotNull UUID holder, Consumer<Optional<PlayerEconomy>> callback);

    // Manipulating player savings

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     */
    void increase(Double amount, Economy economy, FrameworkPlayer holder);

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     */
    void increase(Double amount, Economy economy, UUID holder);

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     */
    void increase(Double amount, UUID economy, FrameworkPlayer holder);

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     */
    void increase(Double amount, UUID economy, UUID holder);

    /**
     * Remove the given amount from the holders' wallet.
     * @param amount Amount that should be removed. This number should be positive.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void decrease(Double amount, Economy economy, FrameworkPlayer holder, Consumer<Boolean> callback);

    /**
     * Remove the given amount from the holders' wallet.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, Economy, UUID)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void decrease(Double amount, Economy economy, UUID holder, Consumer<Boolean> callback);

    /**
     * Remove the given amount from the holders' wallet.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, UUID, FrameworkPlayer)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void decrease(Double amount, UUID economy, FrameworkPlayer holder, Consumer<Boolean> callback);

    /**
     * Remove the given amount from the holders' wallet.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, UUID, UUID)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void decrease(Double amount, UUID economy, UUID holder, Consumer<Boolean> callback);

    /**
     * Sets the amount for the holders' wallet.
     * @param value The end value of the wallet.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void set(Double value, Economy economy, FrameworkPlayer holder);

    /**
     * Sets the amount for the holders' wallet.
     * @param value The end value of the wallet.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void set(Double value, Economy economy, UUID holder);

    /**
     * Sets the amount for the holders' wallet.
     * @param value The end value of the wallet.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void set(Double value, UUID economy, FrameworkPlayer holder);

    /**
     * Sets the amount for the holders' wallet.
     * @param value The end value of the wallet.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void set(Double value, UUID economy, UUID holder);

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     */
    void transfer(Double amount, Economy economy, FrameworkPlayer from, FrameworkPlayer to, Consumer<Boolean> callback);

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     */
    void transfer(Double amount, Economy economy, UUID from, UUID to, Consumer<Boolean> callback);

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     */
    void transfer(Double amount, UUID economy, FrameworkPlayer from, FrameworkPlayer to, Consumer<Boolean> callback);

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     */
    void transfer(Double amount, UUID economy, UUID from, UUID to, Consumer<Boolean> callback);
}
