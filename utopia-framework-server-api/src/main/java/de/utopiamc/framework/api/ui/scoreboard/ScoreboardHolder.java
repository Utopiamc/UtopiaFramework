package de.utopiamc.framework.api.ui.scoreboard;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import org.bukkit.entity.Player;

public interface ScoreboardHolder {

    void bind(Player player);
    void bind(FrameworkPlayer player);

}
