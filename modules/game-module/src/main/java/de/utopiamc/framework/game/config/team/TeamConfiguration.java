package de.utopiamc.framework.game.config.team;

import de.utopiamc.framework.game.config.GameConfig;

public interface TeamConfiguration {

    /**
     * A team will be displayed as {@code full}, if the maximal team size is reached. A full team will not be considered during automatic team allocation.
     *
     * @param size The maximal amount of players, that could join one team.
     * @return This instance (builder pattern)
     */
    TeamConfiguration teamSize(Integer size);

    /**
     * During team creation, this amount will be used to generate the teams. Later an inventory with the perfect size will be created to fit all the teams to choose from.
     *
     * @param max The maximal amount of possible teams (max. 16).
     * @return This instance (builder pattern)
     */
    TeamConfiguration maxTeams(Integer max);

    GameConfig and();

    Integer getTeamSize();
    Integer getMaxTeams();
}
