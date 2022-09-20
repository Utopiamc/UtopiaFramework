package de.utopiamc.framework.module.server.command.runner;

import de.utopiamc.framework.module.server.command.router.RouteItem;

import java.lang.reflect.Method;

public class StaticRouteRunner extends RunnerHelper {

    public StaticRouteRunner(Method run, RouteItem item) {
        super(run, item);
    }

}
