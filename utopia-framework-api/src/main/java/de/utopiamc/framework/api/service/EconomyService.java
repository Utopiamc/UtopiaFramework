package de.utopiamc.framework.api.service;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.model.Economy;
import de.utopiamc.framework.api.model.PlayerEconomy;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface EconomyService {

    // Getting economy types

    Economy[] getEconomies();

    Optional<Economy> getEconomy(UUID id);

    Set<Economy> getEconomy(String name);

    // Getting player savings

    Set<PlayerEconomy> getEconomies(FrameworkPlayer holder);
    Set<PlayerEconomy> getEconomies(UUID holder);

    PlayerEconomy getEconomy(UUID economy, FrameworkPlayer holder);
    PlayerEconomy getEconomy(UUID economy, UUID holder);

    Set<PlayerEconomy> getEconomy(String economy, FrameworkPlayer holder);
    Set<PlayerEconomy> getEconomy(String economy, UUID holder);

    PlayerEconomy getPlayerEconomy(Economy economy, FrameworkPlayer holder);
    PlayerEconomy getPlayerEconomy(Economy economy, UUID holder);

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
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive. Otherwise, use {@link #decrease(Double, String, FrameworkPlayer)}.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     * @deprecated This method will increase the wallet holdings of each economy with the given name.
     */
    @Deprecated
    void increase(Double amount, String economy, FrameworkPlayer holder);

    /**
     * Add the given amount to the holders' wallet.
     * @param amount Amount that should be added. This number should be positive. Otherwise, use {@link #decrease(Double, String, UUID)}.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be added to.
     * @deprecated This method will increase the wallet holdings of each economy with the given name.
     */
    @Deprecated
    void increase(Double amount, String economy, UUID holder);

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
     * Remove the given amount from the holders' wallet.
     * @deprecated This method will decrease the wallet holdings of each economy with the given name.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, Economy, FrameworkPlayer)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     * @return Returns {@code false} if the wallet has to less money.
     */
    @Deprecated
    boolean decrease(Double amount, String economy, FrameworkPlayer holder);

    /**
     * Remove the given amount from the holders' wallet.
     * @deprecated This method will decrease the wallet holdings of each economy with the given name.
     * @param amount Amount that should be removed. This number should be positive. Otherwise, use {@link #increase(Double, Economy, FrameworkPlayer)}
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     * @return Returns {@code false} if the wallet has to less money.
     */
    @Deprecated
    boolean decrease(Double amount, String economy, UUID holder);

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
     * Sets the amount for the holders' wallet.
     * @param value The end value of the wallet.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void set(Double value, String economy, FrameworkPlayer holder);

    /**
     * Sets the amount for the holders' wallet.
     * @param value The end value of the wallet.
     * @param economy The economy that should be used.
     * @param holder The wallet account where the amount should be removed from.
     */
    void set(Double value, String economy, UUID holder);

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

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     * @return Returns false, when the money has to less money. Returns true, when the transaction is complete.
     */
    boolean transfer(Double amount, String economy, FrameworkPlayer from, FrameworkPlayer to);

    /**
     * Transfers the given amount from one wallet to the other.
     * @param amount The value that should be transferred. This value should be positive.
     * @param economy The economy that should be used.
     * @param from The wallet where the money should be taken from.
     * @param to The wallet where the money should be added to.
     * @return Returns false, when the money has to less money. Returns true, when the transaction is complete.
     */
    boolean transfer(Double amount, String economy, UUID from, UUID to);
}
