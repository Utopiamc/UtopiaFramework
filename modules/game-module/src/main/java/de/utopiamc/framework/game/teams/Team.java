package de.utopiamc.framework.game.teams;

import de.utopiamc.framework.game.exception.FullTeamException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Team {

    private final UUID id;
    private final Set<UUID> members;
    private final TeamColor color;
    private final Integer maxSize;

    public Team(TeamColor color, Integer maxSize) {
        this.id = UUID.randomUUID();
        this.members = new HashSet<>();
        this.color = color;
        this.maxSize = maxSize;
    }

    public void addMember(UUID uuid) {
        if (canJoin())
            members.add(uuid);
        else
            throw new FullTeamException(String.format("Team '%s' is full, but '%s' wants to join! There are '%s' player in the team and '%s' are allowed!", getName(), uuid, members.size(), maxSize));
    }

    public boolean canJoin() {
        return !isFull();
    }

    public boolean isFull() {
        return members.size() >= maxSize;
    }

    public UUID getId() {
        return id;
    }

    public TeamColor getColor() {
        return color;
    }

    public String getName() {
        return color.getName();
    }

    public Set<UUID> getMembers() {
        return members;
    }

}
