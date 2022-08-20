package de.utopiamc.framework.api.entity;

import java.util.Date;
import java.util.UUID;

public abstract class FrameworkPlayer {

    private final UUID uuid;
    private final String name;

    private final Date firstJoined;

    public FrameworkPlayer(UUID uuid, String name, Date firstJoined) {
        this.uuid = uuid;
        this.name = name;
        this.firstJoined = firstJoined;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Date getFirstJoined() {
        return firstJoined;
    }
}
