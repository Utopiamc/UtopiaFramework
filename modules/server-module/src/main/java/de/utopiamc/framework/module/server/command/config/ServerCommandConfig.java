package de.utopiamc.framework.module.server.command.config;

import de.utopiamc.framework.api.commands.CommandConfig;
import lombok.ToString;

@ToString
public class ServerCommandConfig implements CommandConfig {

    private Boolean shouldGenerateHelp;
    private Boolean shouldGenerateErrorHandler;

    private char primary;
    private char secondary;
    private char text;
    private char error;

    private String title;
    private String prefix;

    private String permission;
    private String description;

    @Override
    public CommandConfig shouldGenerateHelp(boolean shouldGenerate) {
        this.shouldGenerateHelp = shouldGenerate;
        return this;
    }

    @Override
    public CommandConfig shouldGenerateErrorHandler(boolean shouldGenerate) {
        this.shouldGenerateErrorHandler = shouldGenerate;
        return this;
    }

    @Override
    public CommandConfig primaryColor(char primary) {
        this.primary = primary;
        return this;
    }

    @Override
    public CommandConfig secondaryColor(char secondary) {
        this.secondary = secondary;
        return this;
    }

    @Override
    public CommandConfig errorColor(char error) {
        this.error = error;
        return this;
    }

    @Override
    public CommandConfig textColor(char text) {
        this.text = text;
        return this;
    }

    @Override
    public CommandConfig title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public CommandConfig prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public CommandConfig neededPermission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public CommandConfig description(String description) {
        this.description = description;
        return this;
    }

    public boolean isShouldGenerateHelp() {
        if (shouldGenerateHelp == null) return true;
        return shouldGenerateHelp;
    }

    public boolean isShouldGenerateErrorHandler() {
        if (shouldGenerateHelp == null) return true;
        return shouldGenerateErrorHandler;
    }

    public char getPrimary() {
        return primary;
    }

    public char getSecondary() {
        return secondary;
    }

    public char getText() {
        return text;
    }

    public char getError() {
        return error;
    }

    public String getTitle() {
        return title;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }
}
