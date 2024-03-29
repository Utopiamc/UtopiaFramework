package de.utopiamc.framework.module.server.model;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.events.player.FrameworkPlayerJoinEvent;
import de.utopiamc.framework.api.events.player.FrameworkPlayerQuitEvent;
import de.utopiamc.framework.api.model.TempEventSubscription;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardHolder;
import de.utopiamc.framework.module.server.IntegerFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerScoreboardHolder implements ScoreboardHolder {

    private final ServerScoreboardBuilder builder;

    private boolean autoBind = false;

    private TempEventSubscription autoBindTask;
    private TempEventSubscription autoBindUnBindTask;

    public ServerScoreboardHolder(ServerScoreboardBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void bind(Player player) {
        bind(builder.playerService.get(player.getUniqueId()));
    }

    @Override
    public void bind(FrameworkPlayer p) {
        ServerFrameworkPlayer player = (ServerFrameworkPlayer) p;
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        if (!player.getPlayer().isOnline()) {
            return;
        }

        Objective objective;
        if ((objective = scoreboard.getObjective("sidebar")) == null)
            objective = scoreboard.registerNewObjective("sidebar", "aaa");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(builder.getTitle());

        AtomicInteger lines = new AtomicInteger(0);

        // count lines
        lines.addAndGet(builder.getSpaceBetweenLines());

        for (ServerScoreboardLineBuilder lineBuilder : builder.getLineBuilders()) {
            lines.addAndGet(lineBuilder.requiredLines());
            lines.addAndGet(builder.getSpaceBetweenLines());
        }

        // build lines
        IntegerFunction f = lines::getAndDecrement;

        for (int i = 0; i < builder.getSpaceBetweenLines(); i++) {
            Integer integer = f.get();
            objective.getScore(ServerScoreboardBuilder.SPACES[integer]).setScore(integer);
        }

        for (ServerScoreboardLineBuilder lineBuilder : builder.getLineBuilders()) {
            lineBuilder.registerLine(f, objective, player);

            for (int i = 0; i < builder.getSpaceBetweenLines(); i++) {
                Integer integer = f.get();
                objective.getScore(ServerScoreboardBuilder.SPACES[integer]).setScore(integer);
            }
        }

        ((Player) player.getPlayer()).setScoreboard(scoreboard);
    }

    @Override
    public void autoBind() {
        autoBind = !autoBind;

        if (autoBind)
            enableAutoBind();
        else
            disableAutoBind();
    }

    private void enableAutoBind() {
        Bukkit.getOnlinePlayers().forEach(this::bindPlayer);

        autoBindTask = builder.eventService.subscribe(FrameworkPlayerJoinEvent.class, (event) -> {
            bindPlayer((Player) event.getFrameworkPlayer().getPlayer());
        });
        autoBindUnBindTask = builder.eventService.subscribe(FrameworkPlayerQuitEvent.class, (event) -> {
            builder.getLineBuilders().forEach(b -> b.unbind(event.getFrameworkPlayer().getUuid()));
        });
    }

    private void bindPlayer(Player player) {
        FrameworkPlayer frameworkPlayer = builder.playerService.get(player.getUniqueId());
        bind(frameworkPlayer);
    }

    private void disableAutoBind() {
        autoBindTask.unsubscribe();
    }

}
