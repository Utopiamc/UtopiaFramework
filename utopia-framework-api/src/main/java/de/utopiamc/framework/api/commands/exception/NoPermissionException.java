package de.utopiamc.framework.api.commands.exception;

public class NoPermissionException extends CommandException {

    public NoPermissionException(String permission) {
        super("Dazu hast du keine Rechte! &8(%s)".formatted(permission));
    }
}
