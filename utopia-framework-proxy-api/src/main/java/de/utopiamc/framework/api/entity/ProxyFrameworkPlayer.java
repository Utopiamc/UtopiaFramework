package de.utopiamc.framework.api.entity;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Date;
import java.util.UUID;

public class ProxyFrameworkPlayer extends FrameworkPlayer {

    private final ProxiedPlayer player;

    public ProxyFrameworkPlayer(UUID uuid, String name, Date firstJoined, ProxiedPlayer player) {
        super(uuid, name, firstJoined);
        this.player = player;
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }
}
