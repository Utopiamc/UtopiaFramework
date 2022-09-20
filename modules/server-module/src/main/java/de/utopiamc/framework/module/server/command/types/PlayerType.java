package de.utopiamc.framework.module.server.command.types;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.entity.PlayerServiceHolder;
import de.utopiamc.framework.module.server.command.router.RouteItem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class PlayerType implements RouteType {

    @Override
    public Object resolve(RouteItem item, Class<?> type, List<String> args) {
        try {
            String s = args.get(item.getArg());
            if (OfflinePlayer.class.isAssignableFrom(type)) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
                if (!offlinePlayer.isOnline() && Player.class.isAssignableFrom(type))
                    return null;
                return offlinePlayer;
            }else if (FrameworkPlayer.class.isAssignableFrom(type)) {
                return PlayerServiceHolder.getPlayerService().get(s);
            }
        }catch (IndexOutOfBoundsException ignored) {}
        return null;
    }

    @Override
    public List<String> complete(CommandSender sender, List<String> args, int pointer) {
        String s = args.get(pointer).toLowerCase();

        List<String> completed = new LinkedList<>();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getName().toLowerCase().startsWith(s))
                completed.add(onlinePlayer.getName());
        }

        return completed;
    }
}
