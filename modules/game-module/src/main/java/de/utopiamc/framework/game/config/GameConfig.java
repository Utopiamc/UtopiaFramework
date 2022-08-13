package de.utopiamc.framework.game.config;

import de.utopiamc.framework.api.config.UtopiaConfiguration;
import de.utopiamc.framework.game.config.stats.StatsConfig;
import de.utopiamc.framework.game.config.team.TeamConfiguration;

public interface GameConfig extends UtopiaConfiguration {

    TeamConfiguration enableTeams();
    StatsConfig enableStats();

}
