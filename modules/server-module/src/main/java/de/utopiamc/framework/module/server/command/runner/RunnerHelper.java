package de.utopiamc.framework.module.server.command.runner;

import de.utopiamc.framework.api.service.MessageService;
import de.utopiamc.framework.module.server.command.router.RouteItem;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class RunnerHelper implements Runner {

    protected final Method run;
    protected final RouteItem item;

    protected RunnerHelper(Method run, RouteItem item) {
        this.run = run;
        this.item = item;
    }

    @Override
    public void run(List<String> args, CommandSender sender, MessageService messageService, Object instance) {
        CommandExecuteContext commandExecuteContext = buildContext(args, sender, messageService);

        Object[] parameters = Arrays.stream(run.getParameters())
                .map(commandExecuteContext::resolve0)
                .toArray(Object[]::new);

        try {
            Object invoke = run.invoke(instance, parameters);

            System.out.println(invoke);

            if (invoke != null) {
                if (invoke.getClass().equals(String.class))
                    messageService.sendMessage((String) invoke);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected CommandExecuteContext buildContext(List<String> args, CommandSender sender, MessageService messageService) {
        return new CommandExecuteContext(this, args, sender, messageService);
    }

}
