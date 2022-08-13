package de.utopiamc.framework.api.ui.scoreboard;

public interface ScoreboardLineBuilder {
    ScoreboardBuilder and();

    ScoreboardHolder build();
}
