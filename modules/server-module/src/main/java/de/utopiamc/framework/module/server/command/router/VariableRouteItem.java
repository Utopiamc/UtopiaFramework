package de.utopiamc.framework.module.server.command.router;

import de.utopiamc.framework.module.server.command.types.RouteType;
import lombok.ToString;

import java.util.Map;

@ToString(callSuper = true)
public class VariableRouteItem extends RouteItem {

    private final RouteType type;
    private final String name;

    public VariableRouteItem(String completeRoute, String description, String permission, Map<String, RouteItem> routes, RouteType type, String name, int arg) {
        super(completeRoute, description, permission, routes, arg);
        this.type = type;
        this.name = name;
    }

    public RouteType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
