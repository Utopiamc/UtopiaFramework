package de.utopiamc.framework.module.server.command.types;

import de.utopiamc.framework.module.server.command.router.RouteItem;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class EnumType<T extends Enum<T>> implements RouteType {

    private final Class<T> enumClass;

    public EnumType(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Object resolve(RouteItem item, Class<?> type, List<String> args) {
        try {
            String s = args.get(item.getArg());
            for (T enumConstant : enumClass.getEnumConstants()) {
                if (enumConstant.name().equalsIgnoreCase(s)) {
                    return enumConstant;
                }
            }
        }catch (NullPointerException ignored) {}
        return null;
    }

    @Override
    public List<String> complete(CommandSender sender, List<String> args, int pointer) {
        String s = args.get(pointer).toLowerCase();

        List<String> completed = new LinkedList<>();

        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.name().toLowerCase().startsWith(s)) {
                completed.add(enumConstant.name().toLowerCase());
            }
        }

        return completed;
    }
}
