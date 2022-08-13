package de.utopiamc.framework.module.server.model;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardBuilder;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardHolder;
import de.utopiamc.framework.api.ui.scoreboard.ScoreboardLineBuilder;
import de.utopiamc.framework.module.server.IntegerFunction;
import org.bukkit.scoreboard.Objective;

public abstract class ServerScoreboardLineBuilder implements ScoreboardLineBuilder {

    protected final ServerScoreboardBuilder builder;

    protected ServerScoreboardLineBuilder(ServerScoreboardBuilder builder) {
        this.builder = builder;
    }

    @Override
    public final ScoreboardBuilder and() {
        return builder;
    }

    @Override
    public final ScoreboardHolder build() {
        return builder.build();
    }

    public void registerLine(IntegerFunction integerFunction, Objective objective, ServerFrameworkPlayer player) {

    }

    public Integer requiredLines() {
        return 0;
    }

}
