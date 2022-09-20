package de.utopiamc.framework.module.server.command;

import de.utopiamc.framework.api.commands.AllowedCaller;
import de.utopiamc.framework.api.commands.descriptors.Variable;
import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.module.server.command.exception.CommandIndexException;
import de.utopiamc.framework.module.server.command.router.RouteItem;
import de.utopiamc.framework.module.server.command.router.VariableRouteItem;
import de.utopiamc.framework.module.server.command.runner.StaticRouteRunner;
import de.utopiamc.framework.module.server.command.runner.VariableRouteRunner;
import de.utopiamc.framework.module.server.command.types.*;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RouteHolder {

    public static final Pattern PARAMETER_REGEX = Pattern.compile("<[a-zA-Z\\d]+>");
    public static final Pattern PARAMETER_LINE_REGEX = Pattern.compile("^.*<[a-zA-Z\\d]+>$");

    private final Map<String, RouteHolder> routeHolders;
    private final String route;
    private final RouteHolder parent;

    private AllowedCaller[] allowedCallers;
    private String permission;
    private String description;
    private Method run;
    private String fullRoute;

    private RouteItem item;

    public RouteHolder(String route, RouteHolder parent) {
        this.parent = parent;
        this.route = route;
        this.routeHolders = new HashMap<>();
    }

    public void append(Method method, LinkedList<String> routeFragments, String description, String permission, AllowedCaller[] allowedCallers, String fullRoute) {
        if (routeFragments.size() == 0) {
            this.allowedCallers = allowedCallers;
            this.description = description;
            this.permission = permission;
            this.run = method;
            this.fullRoute = fullRoute;

            return;
        }

        String next = routeFragments.removeFirst();
        RouteHolder routeHolder = routeHolders.get(next);
        if (routeHolder == null) {
            routeHolder = new RouteHolder(next, this);
            routeHolders.put(next, routeHolder);
        }

        routeHolder.append(method, routeFragments, description, permission, allowedCallers, fullRoute);
    }

    public RouteItem build(int arg) {
        Map<String, RouteItem> items = routeHolders.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().build(arg + 1),
                        (a, b) -> b));

        Matcher matcher = PARAMETER_REGEX.matcher(route);
        if (matcher.find()) {
            String name = matcher.group().replace("<", "").replace(">", "");
            item = new VariableRouteItem(fullRoute, description, permission, items, compileType(name), name, arg);
        } else {
            item = new RouteItem(fullRoute, description, permission, items, arg);
        }

        items.values().forEach(s -> s.setParent(item));

        return item;
    }

    public RouteItem resolve() {
        routeHolders.values().forEach(RouteHolder::resolve);
        if (run != null) {
            boolean hasVariables = PARAMETER_LINE_REGEX.matcher(fullRoute).matches();
            if (hasVariables) {
                item.setRunner(new VariableRouteRunner(run, item));
            } else {
                item.setRunner(new StaticRouteRunner(run, item));
            }
        }

        return item;
    }

    private RouteType compileType(String name) {
        for (Parameter parameter : run.getParameters()) {
            Variable annotation = AnnotationUtil.getAnnotation(parameter, Variable.class);
            if (parameter.getName().equalsIgnoreCase(name) || (annotation != null && annotation.value().equalsIgnoreCase(parameter.getName()))) {
                Class<?> type = parameter.getType();
                if (type.equals(String.class)) {
                    return new StringType();
                } else if (type.isEnum()) {
                    return build(type);
                } else if (Number.class.isAssignableFrom(type)) {
                    return new NumberType();
                } else if (CommandSender.class.isAssignableFrom(type) || FrameworkPlayer.class.isAssignableFrom(type)) {
                    return new PlayerType();
                }
            }
        }
        throw new CommandIndexException(String.format("No parameter with name '%s' found.", name));
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> EnumType<T> build(Class<?> tClass) {
        return new EnumType<>((Class<T>) tClass);
    }
}
