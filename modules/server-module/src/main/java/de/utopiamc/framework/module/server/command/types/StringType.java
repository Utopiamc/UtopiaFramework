package de.utopiamc.framework.module.server.command.types;

import de.utopiamc.framework.module.server.command.router.RouteItem;
import org.bukkit.command.CommandSender;

import java.util.List;

public class StringType implements RouteType {

    @Override
    public Object resolve(RouteItem item, Class<?> type, List<String> args) {
        try {
            return args.get(item.getArg());
        }catch (IndexOutOfBoundsException ignored) {

        }
        return null;
    }

    @Override
    public List<String> complete(CommandSender sender, List<String> args, int pointer) {
        return List.of(args.get(pointer));
    }
}
