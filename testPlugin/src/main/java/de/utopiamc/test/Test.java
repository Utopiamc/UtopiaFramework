package de.utopiamc.test;

import com.google.inject.Inject;
import de.utopiamc.framework.api.dropin.annotations.OnEnable;
import de.utopiamc.framework.api.service.ScoreboardFactory;
import de.utopiamc.framework.api.stereotype.Plugin;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardHolder;

@Plugin
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

    }

}
