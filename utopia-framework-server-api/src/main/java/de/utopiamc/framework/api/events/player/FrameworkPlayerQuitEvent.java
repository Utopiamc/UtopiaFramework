package de.utopiamc.framework.api.events.player;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import org.bukkit.event.player.PlayerQuitEvent;

public class FrameworkPlayerQuitEvent extends FrameworkPlayerEvent {

    public FrameworkPlayerQuitEvent(PlayerQuitEvent event, ServerFrameworkPlayer player) {
        super(event, player);
    }

}
