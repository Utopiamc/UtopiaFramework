package de.utopiamc.framework.api.commands;

public interface CommandConfig {

    CommandConfig shouldGenerateHelp(boolean shouldGenerate);
    CommandConfig shouldGenerateErrorHandler(boolean shouldGenerate);

    CommandConfig primaryColor(char primary);
    CommandConfig secondaryColor(char secondary);
    CommandConfig errorColor(char error);
    CommandConfig textColor(char text);

    CommandConfig title(String title);
    CommandConfig prefix(String prefix);

    CommandConfig neededPermission(String permission);

    CommandConfig description(String description);

}
