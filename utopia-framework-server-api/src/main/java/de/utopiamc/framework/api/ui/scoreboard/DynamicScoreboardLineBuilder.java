package de.utopiamc.framework.api.ui.scoreboard;

import de.utopiamc.framework.api.model.VariableEventHandler;

import java.util.function.Supplier;

public interface DynamicScoreboardLineBuilder extends ScoreboardLineBuilder {

    DynamicScoreboardLineBuilder setTitle(String title);

    DynamicScoreboardLineBuilder setContent(String content);

    <E> DynamicScoreboardLineBuilder addDynamicVariable(String name, String defaultValue, Class<E> event, VariableEventHandler<E> handler);
    <E> DynamicScoreboardLineBuilder addDynamicVariable(String name, Supplier<String> defaultValue, Class<E> event, VariableEventHandler<E> handler);

}
