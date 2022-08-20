package de.utopiamc.framework.api.entity;

import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.UUID;

public class ServerFrameworkPlayer extends FrameworkPlayer {

    private final OfflinePlayer player;

    public ServerFrameworkPlayer(UUID uuid, String name, Date firstJoined, OfflinePlayer player) {
        super(uuid, name, firstJoined);
        this.player = player;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}
