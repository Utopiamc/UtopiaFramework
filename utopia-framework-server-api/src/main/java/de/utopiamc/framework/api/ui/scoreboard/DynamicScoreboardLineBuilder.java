package de.utopiamc.framework.api.ui.scoreboard;

public interface DynamicScoreboardLineBuilder extends ScoreboardLineBuilder {

    DynamicScoreboardLineBuilder setTitle(String title);
    DynamicScoreboardLineBuilder setContent(String title);

    ScoreboardLineUpdater setTitle();
    ScoreboardLineUpdater setContent();

}
