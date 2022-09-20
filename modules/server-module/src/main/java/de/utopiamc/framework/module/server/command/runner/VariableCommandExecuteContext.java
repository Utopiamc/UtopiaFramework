package de.utopiamc.framework.module.server.command.runner;

import de.utopiamc.framework.api.commands.descriptors.Variable;
import de.utopiamc.framework.api.service.MessageService;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.module.server.command.router.RouteItem;
import de.utopiamc.framework.module.server.command.router.VariableRouteItem;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Parameter;
import java.util.List;

public class VariableCommandExecuteContext extends CommandExecuteContext {

    private final RouteItem item;

    public VariableCommandExecuteContext(RunnerHelper helper, RouteItem item, List<String> args, CommandSender sender, MessageService messageService) {
        super(helper, args, sender, messageService);
        this.item = item;
    }

    @Override
    protected Object resolve(Parameter parameter) {
        Variable annotation = AnnotationUtil.getAnnotation(parameter, Variable.class);
        String name = parameter.getName();
        if (annotation != null)
            name = annotation.value();

        return resolve(parameter, item, name);
    }

    private Object resolve(Parameter parameter, RouteItem item, String name) {
        if (item instanceof VariableRouteItem) {
            VariableRouteItem varItem = (VariableRouteItem) item;
            if (name.equalsIgnoreCase(varItem.getName())) {
                return varItem.getType().resolve(varItem, parameter.getType(), args);
            }
        }
        if (item.getParent() != null)
            return resolve(parameter, item.getParent(), name);
        return null;
    }

}
