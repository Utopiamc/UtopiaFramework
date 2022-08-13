package de.utopiamc.framework.game.state;

import com.google.inject.Inject;
import de.utopiamc.framework.api.inject.annotations.Component;
import de.utopiamc.framework.api.stereotype.Plugin;
import org.bukkit.Bukkit;

import java.util.Collection;

@Component
public abstract class GameManager {

    @Plugin private org.bukkit.plugin.Plugin plugin;

    private int updateScheduler;
    private Integer updateTicks;

    @Inject
    public void startUp() {
        updateTicks = 20;

        updateScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

        }, 0, updateTicks);
    }

    public abstract Collection<GameState> getPossibleStates();

    public void update(GameState state) {
        state.update();
    }

}
