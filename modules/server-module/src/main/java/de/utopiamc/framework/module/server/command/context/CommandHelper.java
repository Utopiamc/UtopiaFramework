package de.utopiamc.framework.module.server.command.context;

import de.utopiamc.framework.api.commands.exception.NoPermissionException;
import org.bukkit.command.CommandSender;

public abstract class CommandHelper {

    protected void checkPermission(CommandSender sender, String permission) {
        if (permission != null && !sender.hasPermission(permission))
            throw new NoPermissionException(permission);
    }

    protected char resolveConfig(char c1, char c2) {
        return c1 == Character.UNASSIGNED ? c2 : c1;
    }

    protected String resolveConfig(String s1, String s2) {
        return s1 == null ? s2 : s1;
    }


}
