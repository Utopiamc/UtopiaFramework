package de.utopiamc.framework.module.server.command.service;

import lombok.Value;

@Value
public class CommandVariableHolder {

    public CommandVariableHolder(String title, String prefix, char primary, char secondary, char text, char error) {
        this.title = title;
        this.prefix = prefix == null ? title + " §8• $r" : prefix;
        this.primary = primary;
        this.secondary = secondary;
        this.text = text;
        this.error = error;
    }

    String title;
    String prefix;

    char primary;
    char secondary;
    char text;
    char error;

}
