package de.utopiamc.framework.module.server.command.router;

import de.utopiamc.framework.module.server.command.runner.Runner;

import java.util.Map;

public class RouteItem {
    private final String description;
    private final String permission;
    private final Map<String, RouteItem> routes;
    private final String completeRoute;

    private final int arg;

    private Runner runner;

    private RouteItem parent;

    public RouteItem(String completeRoute, String description, String permission, Map<String, RouteItem> routes, int arg) {
        this.description = description;
        this.permission = permission;
        this.routes = routes;
        this.completeRoute = completeRoute;
        this.arg = arg;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    public Map<String, RouteItem> getRoutes() {
        return routes;
    }

    public String getCompleteRoute() {
        return completeRoute;
    }

    public RouteItem getParent() {
        return parent;
    }

    public void setParent(RouteItem parent) {
        this.parent = parent;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public int getArg() {
        return arg;
    }
}
