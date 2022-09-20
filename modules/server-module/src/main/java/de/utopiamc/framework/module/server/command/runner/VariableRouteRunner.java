package de.utopiamc.framework.module.server.command.runner;

import de.utopiamc.framework.api.service.MessageService;
import de.utopiamc.framework.module.server.command.router.RouteItem;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.List;

public class VariableRouteRunner extends RunnerHelper {

    public VariableRouteRunner(Method run, RouteItem item) {
        super(run, item);
    }

    @Override
    protected CommandExecuteContext buildContext(List<String> args, CommandSender sender, MessageService messageService) {
        return new VariableCommandExecuteContext(this, item, args, sender, messageService);
    }
}
