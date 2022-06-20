package de.utopiamc.framework.common.exceptions;

public class DropInSourceLoaderException extends RuntimeException {

    public DropInSourceLoaderException(String message) {
        super(message);
    }

    public DropInSourceLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
