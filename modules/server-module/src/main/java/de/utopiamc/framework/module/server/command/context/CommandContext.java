package de.utopiamc.framework.module.server.command.context;

import de.utopiamc.framework.api.commands.AllowedCaller;
import de.utopiamc.framework.api.commands.CommandConfig;
import de.utopiamc.framework.api.commands.descriptors.*;
import de.utopiamc.framework.api.commands.exception.CommandException;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.service.ColorService;
import de.utopiamc.framework.api.service.MessageService;
import de.utopiamc.framework.api.stereotype.Command;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.module.server.command.RouteBuilder;
import de.utopiamc.framework.module.server.command.CommandImpl;
import de.utopiamc.framework.module.server.command.RouteHolder;
import de.utopiamc.framework.module.server.command.config.ServerCommandConfig;
import de.utopiamc.framework.module.server.command.exception.CommandIndexException;
import de.utopiamc.framework.module.server.command.router.RouteItem;
import de.utopiamc.framework.module.server.command.router.VariableRouteItem;
import de.utopiamc.framework.module.server.command.service.CommandColorService;
import de.utopiamc.framework.module.server.command.service.CommandMessageService;
import de.utopiamc.framework.module.server.command.service.CommandVariableHolder;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

@ToString
public class CommandContext extends CommandHelper {

    private final Logger logger = Logger.getLogger(CommandContext.class.getSimpleName());

    private final Object commandClassInstance;
    private final Command commandInfo;
    private final UtopiaMetaConfig metaConfig;

    private ServerCommandConfig config;
    private Map<String, RouteItem> routes;

    public CommandContext(Object commandClassInstance, Command commandInfo, UtopiaMetaConfig metaConfig) {
        this.commandClassInstance = commandClassInstance;
        this.commandInfo = commandInfo;
        this.metaConfig = metaConfig;

        configure();
        register();
    }

    public void register() {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            CommandMap commandMap1 = (CommandMap) commandMap.get(Bukkit.getServer());
            commandMap1.register(commandInfo.value(), new CommandImpl(this));
        }catch (Throwable t) {
            logger.severe("Failed to register command %s. %s".formatted(commandInfo.value(), t.getMessage()));
            t.printStackTrace();
        }
    }


    public void execute(CommandSender sender, String[] args, String label) {
        CommandVariableHolder holder = new CommandVariableHolder(resolveConfig(config.getTitle(), metaConfig.getTitle()),
                resolveConfig(config.getPrefix(), metaConfig.getPrefix()),
                resolveConfig(config.getPrimary(), metaConfig.getPrimaryColor()),
                resolveConfig(config.getSecondary(), metaConfig.getSecondaryColor()),
                resolveConfig(config.getText(), metaConfig.getTextColor()),
                resolveConfig(config.getError(), metaConfig.getErrorColor()));
        ColorService colorService = new CommandColorService(holder);
        CommandMessageService messageService = new CommandMessageService(colorService, sender, holder);

        try {
            execute0(sender, args, label, messageService);
        }catch (CommandException e) {
            messageService.sendError(e.getMessage());
        } catch (Throwable e) {
            logger.warning(String.format("Failed to execute command %s, %s", commandInfo.value(), e.getMessage()));
            messageService.sendError("Es ist ein Fehler beim ausführen des Commandes aufgetreten.");
            e.printStackTrace();
        }
    }

    private void execute0(CommandSender sender, String[] args, String label, MessageService messageService) {
        checkPermission(sender, config.getPermission());

        String route = "";
        if (args.length != 0)
            route = args[0];

        RouteItem routeItem = routes.get(route);
        if (routeItem != null) {
            followRoute(List.of(args), 0, routeItem, sender, messageService);
        } else {
            boolean followed = false;
            for (String s : routes.keySet()) {
                if (RouteHolder.PARAMETER_REGEX.matcher(s).matches()) {
                    followed = true;
                    followRoute(List.of(args), 0, routes.get(s), sender, messageService);
                }
            }

            if (!followed) {
                // TODO: Handle Help
                System.out.println("Help follow");
            }
        }
    }

    protected void followRoute(List<String> args, int pointer, RouteItem routeItem, CommandSender sender, MessageService messageService) {
        System.out.println(routeItem.getCompleteRoute());
        if (args.size() <= pointer+1) {
            if (routeItem.getRunner() != null) {
                routeItem.getRunner().run(args, sender, messageService, commandClassInstance);
            } else {
                // TODO: Handle Help
                System.out.println("Help no run");
            }
        } else {
            pointer += 1;

            for (String s : routeItem.getRoutes().keySet()) {
                if (RouteHolder.PARAMETER_REGEX.matcher(s).matches() || s.equalsIgnoreCase(args.get(pointer))) {
                    followRoute(args, pointer, routeItem.getRoutes().get(s), sender, messageService);
                    return;
                }
            }

            // TODO: Help
            System.out.println("Help");

        }
    }

    private void configure() {
        config = new ServerCommandConfig();
        for (Map.Entry<Class<? extends Annotation>, Set<Method>> entry : scanCommandMethods().entrySet()) {
            if (entry.getKey().equals(MapRoute.class)) {
                routes = setupRoutes(entry.getValue());
            } else if (entry.getKey().equals(Configure.class)) {
                config = setupConfig(entry.getValue());
            }
        }

        if (routes == null || routes.isEmpty())
            throw new CommandIndexException("No mapped routes found, but at least one is required.");
    }

    private ServerCommandConfig setupConfig(Set<Method> methods) {
        for (Method method : methods) {
            if (method.getParameters().length != 1 || !CommandConfig.class.isAssignableFrom(method.getParameters()[0].getType())) {
                throw new CommandIndexException(String.format("Invalid config method %s. Should have one parameter of type %s.", method, CommandConfig.class));
            }

            try {
                method.invoke(commandClassInstance, config);
            } catch (Throwable e) {
                e.printStackTrace();
                throw new CommandIndexException(String.format("Failed to configure command config %s.", method));
            }
        }

        return config;
    }

    private Map<String, RouteItem> setupRoutes(Set<Method> methods) {
        RouteBuilder builder = new RouteBuilder();

        for (Method method : methods) {
            Set<Annotation> annotations = AnnotationUtil.getAnnotationsWithAnnotation(method, CommandAnnotation.class);

            String route = "";
            String permission = null;
            AllowedCaller[] callers = null;

            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(MapRoute.class)) {
                    route = ((MapRoute)annotation).value();
                } else if (annotation.annotationType().equals(Permission.class)) {
                    permission = ((Permission)annotation).value();
                } else if (annotation.annotationType().equals(Allow.class)) {
                    callers = ((Allow)annotation).value();
                }
            }

            builder.append(method, route, "", permission, callers);
        }

        return builder.build();
    }

    private Map<Class<? extends Annotation>, Set<Method>> scanCommandMethods() {
        Map<Class<? extends Annotation>, Set<Method>> methods = new HashMap<>();
        for (Method method : commandClassInstance.getClass().getMethods()) {
            Set<Annotation> annotationsWithAnnotation = AnnotationUtil.getAnnotationsWithAnnotation(method, CommandAnnotation.class);

            for (Annotation annotation : annotationsWithAnnotation) {
                Set<Method> methods1 = methods.computeIfAbsent(annotation.annotationType(), k -> new HashSet<>());
                methods1.add(method);
            }

        }
        return methods;
    }

    public ServerCommandConfig getConfig() {
        return config;
    }

    public Command getCommandInfo() {
        return commandInfo;
    }

    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (routes == null)
            return List.of();

        return completeRoute(sender, List.of(args), 0, routes);
    }

    private List<String> completeRoute(CommandSender sender, List<String> args, int pointer, Map<String, RouteItem> routes) {
        if (args.size() > pointer+1) {
            return completeRoute(sender, args, pointer+1, resolveRoute(sender, args, pointer, routes));
        }

        List<String> completed = new LinkedList<>();

        for (String s : routes.keySet()) {
            RouteItem item1 = routes.get(s);

            if (item1.getPermission() != null && !sender.hasPermission(item1.getPermission()))
                continue;

            if (RouteHolder.PARAMETER_REGEX.matcher(s).matches()) {
                completed.addAll(((VariableRouteItem) item1).getType().complete(sender, args, pointer));
            }else if (s.startsWith(args.get(pointer))) {
                completed.add(s);
            }
        }

        return completed;
    }

    private Map<String, RouteItem> resolveRoute(CommandSender sender, List<String> args, int pointer, Map<String, RouteItem> routes) {
        String arg = args.get(pointer);

        for (String s : routes.keySet()) {
            if (RouteHolder.PARAMETER_REGEX.matcher(s).matches() || s.equalsIgnoreCase(arg)) {
                return routes.get(s).getRoutes();
            }
        }
        return Map.of();
    }


}
