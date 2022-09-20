package de.utopiamc.framework.api.ui.scoreboard;

public interface ScoreboardBuilder {

    ScoreboardBuilder title(String title);
    ScoreboardBuilder shouldSpaceLines(Boolean value);
    ScoreboardBuilder shouldSpaceLines(Integer space);

    ScoreboardBuilder titlePrefix(String prefix);
    ScoreboardBuilder contentPrefix(String prefix);

    StaticScoreboardLineBuilder addLine();
    DynamicScoreboardLineBuilder addDynamicLine();

    ScoreboardHolder build();
}
