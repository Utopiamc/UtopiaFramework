package de.utopiamc.framework.api.ui.scoreboard;

public interface DynamicScoreboardLineBuilder extends ScoreboardLineBuilder {

    DynamicScoreboardLineBuilder setTitle(String title);
    DynamicScoreboardLineBuilder setTitle(ScoreboardSubscribeable title);
    DynamicScoreboardLineBuilder setContent(String content);
    DynamicScoreboardLineBuilder setContent(ScoreboardSubscribeable content);

    ScoreboardLineUpdater setTitle();
    ScoreboardLineUpdater setContent();

}
