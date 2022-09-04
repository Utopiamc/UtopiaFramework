package de.utopiamc.framework.common.exceptions;

public class StompParseException extends Exception {

    public StompParseException() {
    }

    public StompParseException(String message) {
        super(message);
    }

    public StompParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public StompParseException(Throwable cause) {
        super(cause);
    }
}
