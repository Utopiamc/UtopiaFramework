package de.utopiamc.framework.game.services;

import de.utopiamc.framework.game.teams.Team;

import java.util.Collection;

public interface TeamService {

    Collection<Team> createTeams();

    Collection<Team> createTeams(int maxTeams, int teamSize);

}
