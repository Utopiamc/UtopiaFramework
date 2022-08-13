package de.utopiamc.framework.api.ui.scoreboard;

public interface ScoreboardBuilder {

    ScoreboardBuilder title(String title);
    ScoreboardBuilder shouldSpaceLines(Boolean value);
    ScoreboardBuilder shouldSpaceLines(Integer space);

    StaticScoreboardLineBuilder addLine();
    DynamicScoreboardLineBuilder addDynamicLine();

    ScoreboardHolder build();
}
