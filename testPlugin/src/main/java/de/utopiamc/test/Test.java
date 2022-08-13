package de.utopiamc.test;

import com.google.inject.Inject;
import de.utopiamc.framework.api.event.Subscribe;
import de.utopiamc.framework.api.event.qualifier.Event;
import de.utopiamc.framework.api.service.ScoreboardFactory;
import de.utopiamc.framework.api.stereotype.Controller;
import de.utopiamc.framework.api.stereotype.Plugin;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;

@Plugin
@Cool
@Controller
public class Test {

    private final ScoreboardFactory scoreboardFactory;

    @Inject
    public Test(ScoreboardFactory scoreboardFactory) {
        this.scoreboardFactory = scoreboardFactory;
    }

    @Subscribe(event = PlayerJoinEvent.class)
    public void join(@Event PlayerJoinEvent playerEvent) {
        ScoreboardHolder $pCool = scoreboardFactory.createScoreboard()
                .addLine()
                    .setTitle("$p§lTest")
                    .setContent("§8§l» $sHi")
                    .and()
                .addDynamicLine()
                .setContent("Ich bin %online%")
                .addDynamicVariable("online", () -> String.valueOf(Bukkit.getOnlinePlayers().size()), PlayerJoinEvent.class, (player, event) -> String.valueOf(Bukkit.getOnlinePlayers().size()))
                .build();
        $pCool.bind(playerEvent.getPlayer());
    }

}
