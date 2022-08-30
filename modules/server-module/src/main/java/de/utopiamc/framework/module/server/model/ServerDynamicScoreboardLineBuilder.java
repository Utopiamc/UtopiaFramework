package de.utopiamc.framework.module.server.model;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.ui.scoreboard.DynamicScoreboardLineBuilder;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardLineUpdater;
import de.utopiamc.framework.module.server.IntegerFunction;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ServerDynamicScoreboardLineBuilder extends ServerScoreboardLineBuilder implements DynamicScoreboardLineBuilder {


    private String title;
    private ServerScoreboardLineUpdater titleUpdater;
    private String content;
    private ServerScoreboardLineUpdater contentUpdater;

    public ServerDynamicScoreboardLineBuilder(ServerScoreboardBuilder builder) {
        super(builder);
    }

    @Override
    public DynamicScoreboardLineBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public DynamicScoreboardLineBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public ScoreboardLineUpdater setTitle() {
        this.titleUpdater = new ServerScoreboardLineUpdater(this::setTeamValue);
        return titleUpdater;
    }

    @Override
    public ScoreboardLineUpdater setContent() {
        this.contentUpdater = new ServerScoreboardLineUpdater(this::setTeamValue);
        return contentUpdater;
    }

    @Override
    public Integer requiredLines() {
        int i = 0;
        if (title!=null || contentUpdater != null)
            i++;
        if (content!=null || contentUpdater != null)
            i++;
        return i;
    }

    @Override
    public void registerLine(IntegerFunction integerFunction, Objective objective, ServerFrameworkPlayer player) {
        registerLine(integerFunction, objective, player, title, titleUpdater);
        registerLine(integerFunction, objective, player, content, contentUpdater);
    }

    private void registerLine(IntegerFunction integerFunction, Objective objective, ServerFrameworkPlayer player, String value, ServerScoreboardLineUpdater scoreboardLineUpdater) {
        if (value != null) {
            Integer integer = integerFunction.get();
            objective.getScore(value).setScore(integer);
        } else if (scoreboardLineUpdater != null) {
            Integer integer = integerFunction.get();

            Scoreboard scoreboard = objective.getScoreboard();
            String key = "";

            Team team = scoreboard.getTeam(key);
            if (team == null) {
                team = scoreboard.registerNewTeam(key);
            }
            team.addEntry(key);

            String defaultValue = scoreboardLineUpdater.onBound(player);
            if (defaultValue == null)
                defaultValue = "EMPTY";

            setTeamValue(defaultValue, team);

            objective.getScore(key).setScore(integer);

            scoreboardLineUpdater.put(player.getUuid(), team);
        }
    }

    private void setTeamValue(String value, Team team) {
        value = builder.colorService.translateColors(value);
        if (value.length() > 64) {
            team.setPrefix(value.substring(0, 63));
            team.setSuffix(value.substring(64));
        }else {
            team.setPrefix(value);
        }
    }
}
