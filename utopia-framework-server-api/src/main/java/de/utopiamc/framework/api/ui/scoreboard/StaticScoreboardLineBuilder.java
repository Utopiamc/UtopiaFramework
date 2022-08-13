package de.utopiamc.framework.api.ui.scoreboard;

public interface StaticScoreboardLineBuilder extends ScoreboardLineBuilder {

    StaticScoreboardLineBuilder setTitle(String title);

    StaticScoreboardLineBuilder setContent(String content);

}
