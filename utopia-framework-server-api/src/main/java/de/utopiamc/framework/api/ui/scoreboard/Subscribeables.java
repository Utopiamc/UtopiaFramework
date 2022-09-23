package de.utopiamc.framework.api.ui.scoreboard;

import com.google.inject.Inject;
import de.utopiamc.framework.api.events.player.FrameworkPlayerJoinEvent;
import de.utopiamc.framework.api.events.player.FrameworkPlayerQuitEvent;
import de.utopiamc.framework.api.service.TempEventService;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class Subscribeables {

    @Inject
    public static TempEventService eventService;

    public static ScoreboardSubscribeable playerCount() {
        return updater -> {
            updater.onBound((player) -> String.valueOf(Bukkit.getOnlinePlayers().size()));

            eventService.subscribe(FrameworkPlayerJoinEvent.class, (event) -> updater.update(String.valueOf(Bukkit.getOnlinePlayers().size())));
            eventService.subscribe(FrameworkPlayerQuitEvent.class, (event) -> updater.update(String.valueOf(Bukkit.getOnlinePlayers().size())));
        };
    }

}
