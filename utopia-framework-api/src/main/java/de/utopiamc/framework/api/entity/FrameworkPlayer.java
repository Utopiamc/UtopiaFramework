package de.utopiamc.framework.api.entity;

import java.util.UUID;

public abstract class FrameworkPlayer {

    private final UUID uuid;
    private final String name;

    public FrameworkPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }
}
