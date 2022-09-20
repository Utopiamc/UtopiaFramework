package de.utopiamc.framework.module.server.command.service;

import de.utopiamc.framework.api.service.ColorService;
import de.utopiamc.framework.api.service.MessageService;
import org.bukkit.command.CommandSender;

public class CommandMessageService implements MessageService {

    private final ColorService colorService;
    private final CommandSender sender;
    private final CommandVariableHolder variableHolder;

    public CommandMessageService(ColorService colorService, CommandSender sender, CommandVariableHolder variableHolder) {
        this.colorService = colorService;
        this.sender = sender;
        this.variableHolder = variableHolder;
    }

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(format(message, false));
    }

    @Override
    public void sendError(String message) {
        sender.sendMessage(format(message, true));
    }

    private String format(String message, boolean error) {
        String s = colorService.translateColors(message, error);
        String prefix = colorService.translateColors(variableHolder.getPrefix());

        if (!s.startsWith(prefix))
            s = prefix + s;

        return s;
    }

}
