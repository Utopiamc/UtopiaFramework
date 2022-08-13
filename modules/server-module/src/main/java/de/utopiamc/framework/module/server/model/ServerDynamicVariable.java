package de.utopiamc.framework.module.server.model;

import de.utopiamc.framework.api.model.VariableEventHandler;
import lombok.Data;

import java.util.function.Supplier;

@Data
public class ServerDynamicVariable<E> {

    private final String teamName;
    private final Supplier<String> defaultValue;
    private final String replace;
    private final ServerDynamicScoreboardLineBuilder builder;
    private final String key;

    private final Class<E> event;

    private final VariableEventHandler<E> handler;

    public ServerDynamicVariable(ServerDynamicScoreboardLineBuilder builder, String name, Supplier<String> defaultValue, Class<E> event, VariableEventHandler<E> handler) {
        this.defaultValue = defaultValue;
        this.replace = name;
        this.builder = builder;
        this.event = event;
        this.handler = handler;
        this.key = ServerScoreboardBuilder.VAR_KEYS[builder.varKeyCount];
        this.teamName = key;
        builder.varKeyCount++;
    }

    public String getDefaultValue() {
        return defaultValue.get();
    }
}
