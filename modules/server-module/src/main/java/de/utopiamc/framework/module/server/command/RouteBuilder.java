package de.utopiamc.framework.module.server.command;

import de.utopiamc.framework.api.commands.AllowedCaller;
import de.utopiamc.framework.module.server.command.router.RouteItem;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class RouteBuilder {

    private final Map<String, RouteHolder> routeHolders;

    public RouteBuilder() {
        this.routeHolders = new HashMap<>();
    }

    public void append(Method method, String route, String description, String permission, AllowedCaller[] allowedCallers) {
        LinkedList<String> routeFragments = new LinkedList<>(Arrays.stream(route.trim().split(" ")).toList());

        String firstRoute = routeFragments.removeFirst();
        RouteHolder routeHolder = routeHolders.get(firstRoute);
        if (routeHolder == null) {
            routeHolder = new RouteHolder(firstRoute, null);
            routeHolders.put(firstRoute, routeHolder);
        }

        routeHolder.append(method, routeFragments, description, permission, allowedCallers, route);
    }

    public Map<String, RouteItem> build() {
        return routeHolders.entrySet().stream()
                .peek(e -> e.getValue().build(0))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().resolve(),
                        (a, b) -> b));
    }


}
