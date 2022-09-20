package de.utopiamc.framework.module.server.command.exception;

public class CommandIndexException extends RuntimeException {

    public CommandIndexException() {
    }

    public CommandIndexException(String message) {
        super(message);
    }

    public CommandIndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
