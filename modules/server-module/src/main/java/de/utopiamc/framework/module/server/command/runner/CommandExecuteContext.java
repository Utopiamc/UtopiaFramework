package de.utopiamc.framework.module.server.command.runner;

import de.utopiamc.framework.api.service.MessageService;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Parameter;
import java.util.List;

public class CommandExecuteContext {

    private final RunnerHelper helper;
    protected final List<String> args;
    private final CommandSender sender;
    private final MessageService messageService;

    public CommandExecuteContext(RunnerHelper helper, List<String> args, CommandSender sender, MessageService messageService) {
        this.helper = helper;
        this.args = args;
        this.sender = sender;
        this.messageService = messageService;
    }

    public final Object resolve0(Parameter parameter) {
        Object resolved = resolve(parameter);
        if (resolved != null)
            return resolved;

        if (parameter.getType().isAssignableFrom(sender.getClass())) {
            return sender;
        }

        if (parameter.getType().equals(String[].class)) {
            return args.toArray(String[]::new);
        }

        if (parameter.getType().isAssignableFrom(messageService.getClass())) {
            return messageService;
        }

        return null;
    }

    protected Object resolve(Parameter parameter) {
        return null;
    }

}
