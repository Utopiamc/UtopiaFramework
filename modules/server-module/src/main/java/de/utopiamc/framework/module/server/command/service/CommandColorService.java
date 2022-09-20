package de.utopiamc.framework.module.server.command.service;

import de.utopiamc.framework.api.service.ColorService;

public class CommandColorService implements ColorService {

    private static final char COLOR_SYMBOL = '$';
    private static final String VALID_COLOR_CHARS = "pres";
    private final CommandVariableHolder variableHolder;

    public CommandColorService(CommandVariableHolder variableHolder) {
        this.variableHolder = variableHolder;
    }

    @Override
    public String translateColors(String string) {
        return translateColors(string, false);
    }

    @Override
    public String translateColors(String string, boolean error) {
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == COLOR_SYMBOL) {
                char key = Character.toLowerCase(chars[i + 1]);
                if (VALID_COLOR_CHARS.contains(String.valueOf(key))) {
                    chars[i] = '§';
                    if (key == 'p')
                        chars[i+1] = variableHolder.getPrimary();
                    else if (key == 's')
                        chars[i+1] = variableHolder.getSecondary();
                    else if (key == 'e')
                        chars[i+1] = variableHolder.getError();
                    else
                        chars[i+1] = variableHolder.getText();
                }
            }
        }

        return new String(chars).replaceAll("&", "§");
    }

}
