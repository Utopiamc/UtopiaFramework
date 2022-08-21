package de.utopiamc.framework.api.service;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.model.Economy;
import de.utopiamc.framework.api.model.PlayerEconomy;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface EconomyService {

    // Getting economy types

    @NotNull
    Economy[] getEconomies();

    @NotNull
    Optional<Economy> getEconomy(@NotNull UUID id);

    @NotNull
    Set<Economy> getEconomy(@NotNull String name);

    // Getting player savings

    @NotNull
    Set<PlayerEconomy> getEconomies(@NotNull FrameworkPlayer holder);

    @NotNull
    Set<PlayerEconomy> getEconomies(@NotNull UUID holder);

    @NotNull
    Optional<PlayerEconomy> getEconomy(@NotNull UUID economy, @NotNull FrameworkPlayer holder);

    @NotNull
    Optional<PlayerEconomy> getEconomy(@NotNull UUID economy, @NotNull UUID holder);

    @NotNull
    Set<PlayerEconomy> getEconomy(@NotNull String economy, @NotNull FrameworkPlayer holder);

    @NotNull
    Set<PlayerEconomy> getEconomy(@NotNull String economy, @NotNull UUID holder);

    @NotNull
    Optional<PlayerEconomy> getEconomy(@NotNull Economy economy, @NotNull FrameworkPlayer holder);

    @NotNull
    Optional<PlayerEconomy> getEconomy(@NotNull Economy economy, @NotNull UUID holder);

    // Manipulating player savings

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive. Otherwise, use {@link #decrease(Double, Economy, FrameworkPlayer)}.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     */
    void increase(Double amount, Economy economy, FrameworkPlayer holder);

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive. Otherwise, use {@link #decrease(Double, Economy, UUID)}.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     */
    void increase(Double amount, Economy economy, UUID holder);

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive. Otherwise, use {@link #decrease(Double, UUID, FrameworkPlayer)}.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     */
    void increase(Double amount, UUID economy, FrameworkPlayer holder);

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive. Otherwise, use {@link #decrease(Double, UUID, UUID)}.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     */
    void increase(Double amount, UUID economy, UUID holder);

    /**
     * Remove the given amount from the holders' wallet.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, Economy, FrameworkPlayer)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     * @return Returns {@code false} if the wallet has to less money.
     */
    boolean decrease(Double amount, Economy economy, FrameworkPlayer holder);

    /**
     * Remove the given amount from the holders' wallet.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, Economy, UUID)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     * @return Returns {@code false} if the wallet has to less money.
     */
    boolean decrease(Double amount, Economy economy, UUID holder);

    /**
     * Remove the given amount from the holders' wallet.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, UUID, FrameworkPlayer)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     * @return Returns {@code false} if the wallet has to less money.
     */
    boolean decrease(Double amount, UUID economy, FrameworkPlayer holder);

    /**
     * Remove the given amount from the holders' wallet.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, UUID, UUID)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     * @return Returns {@code false} if the wallet has to less money.
     */
    boolean decrease(Double amount, UUID economy, UUID holder);

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
     * @return Returns false, when the money has to less money. Returns true, when the transaction is complete.
     */
    boolean transfer(Double amount, Economy economy, FrameworkPlayer from, FrameworkPlayer to);

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     * @return Returns false, when the money has to less money. Returns true, when the transaction is complete.
     */
    boolean transfer(Double amount, Economy economy, UUID from, UUID to);

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     * @return Returns false, when the money has to less money. Returns true, when the transaction is complete.
     */
    boolean transfer(Double amount, UUID economy, FrameworkPlayer from, FrameworkPlayer to);

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     * @return Returns false, when the money has to less money. Returns true, when the transaction is complete.
     */
    boolean transfer(Double amount, UUID economy, UUID from, UUID to);
}
