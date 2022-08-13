package de.utopiamc.framework.api.entity;

import org.bukkit.entity.Player;

public class ServerFrameworkPlayer extends FrameworkPlayer {

    private final Player player;

    public ServerFrameworkPlayer(Player player) {
        super(player.getUniqueId(), player.getName());

        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
