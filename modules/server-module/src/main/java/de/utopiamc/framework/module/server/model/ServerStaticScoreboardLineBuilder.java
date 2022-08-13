package de.utopiamc.framework.module.server.model;

import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.ui.scoreboard.StaticScoreboardLineBuilder;
import de.utopiamc.framework.module.server.IntegerFunction;
import org.bukkit.scoreboard.Objective;

public class ServerStaticScoreboardLineBuilder extends ServerScoreboardLineBuilder implements StaticScoreboardLineBuilder {

    private String title;
    private String content;

    public ServerStaticScoreboardLineBuilder(ServerScoreboardBuilder builder) {
        super(builder);
    }

    @Override
    public StaticScoreboardLineBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public StaticScoreboardLineBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public Integer requiredLines() {
        int i = 0;
        if (title!=null)
            i++;
        if (content!=null)
            i++;
        return i;
    }

    @Override
    public void registerLine(IntegerFunction integerFunction, Objective objective, ServerFrameworkPlayer player) {
        if (title != null)
            objective.getScore(builder.colorService.translateColors(title)).setScore(integerFunction.get());

        if (content != null)
            objective.getScore(builder.colorService.translateColors(content)).setScore(integerFunction.get());
    }
}
