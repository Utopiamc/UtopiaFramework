package de.utopiamc.framework.game.config.stats;

import de.utopiamc.framework.game.config.GameConfig;

public interface StatsConfig {

    /**
     * Should the Framework display a board of the top players or top teams. Whether a top 3 or top 10 board is displayed depends on the number of players or teams.
     *
     * @param display If {@code false} nothing is displayed at the end of the game.
     * @return This instance (builder pattern)
     */
    StatsConfig displayTopBoard(boolean display);

    GameConfig and();

}
