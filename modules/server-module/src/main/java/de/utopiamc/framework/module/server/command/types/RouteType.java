package de.utopiamc.framework.module.server.command.types;

import de.utopiamc.framework.module.server.command.router.RouteItem;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface RouteType {
    Object resolve(RouteItem item, Class<?> type, List<String> args);

    List<String> complete(CommandSender sender, List<String> args, int pointer);

}
