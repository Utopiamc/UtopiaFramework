package de.utopiamc.framework.api.events.player;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;

public class FrameworkPlayerJoinEvent extends FrameworkPlayerEvent {

    public FrameworkPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event, ServerFrameworkPlayer player) {
        super(event, player);
    }

}
