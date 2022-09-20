package de.utopiamc.framework.module.server.command;

import de.utopiamc.framework.module.server.command.context.CommandContext;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CommandImpl extends Command {

    private final CommandContext context;

    public CommandImpl(CommandContext context) {
        super(context.getCommandInfo().value(), context.getConfig().getDescription(), "", Arrays.asList(context.getCommandInfo().aliases()));
        this.context = context;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        context.execute(sender, args, commandLabel);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return context.tabComplete(sender, args);
    }
}
