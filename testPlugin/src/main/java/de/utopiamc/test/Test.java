package de.utopiamc.test;

import com.google.inject.Inject;
import de.utopiamc.framework.api.dropin.annotations.OnEnable;
import de.utopiamc.framework.api.service.ScoreboardFactory;
import de.utopiamc.framework.api.stereotype.Controller;
import de.utopiamc.framework.api.stereotype.Plugin;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardBuilder;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardHolder;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardLineUpdater;
import org.bukkit.Bukkit;

@Plugin
@Cool
@Controller
public class Test {

    private int i = 0;

    private final ScoreboardFactory scoreboardFactory;

    private ScoreboardHolder holder;

    @Inject
    public Test(ScoreboardFactory scoreboardFactory) {
        this.scoreboardFactory = scoreboardFactory;
    }

    @OnEnable
    public void onEnable() {
        ScoreboardBuilder scoreboardBuilder = scoreboardFactory.createScoreboard()
                .shouldSpaceLines(1);

        ScoreboardLineUpdater scoreboardLineUpdater = scoreboardBuilder.addDynamicLine().setTitle("$p&lTest")
                .setContent();
        scoreboardLineUpdater.onBound((player) -> String.valueOf(i));

        holder = scoreboardBuilder.build();
        holder.autoBind();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("UtopiaFramework-ServerImplementation"),
                () -> {
            i++;
            scoreboardLineUpdater.update(String.valueOf(i));
                },
                0,
                20);
    }

}
