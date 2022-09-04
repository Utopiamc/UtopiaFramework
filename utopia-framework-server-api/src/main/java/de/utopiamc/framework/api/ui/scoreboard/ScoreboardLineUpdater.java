package de.utopiamc.framework.api.ui.scoreboard;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;

import java.util.UUID;

public interface ScoreboardLineUpdater {

    void update(String value);
    void update(String value, UUID player);

    void onBound(OnBound onBound);

    @FunctionalInterface
    interface OnBound {

        String handle(ServerFrameworkPlayer player);

    }

}
