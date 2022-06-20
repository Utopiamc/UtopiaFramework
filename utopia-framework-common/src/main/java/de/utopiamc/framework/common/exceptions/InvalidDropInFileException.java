package de.utopiamc.framework.common.exceptions;

public class InvalidDropInFileException extends RuntimeException {

    public InvalidDropInFileException(String message) {
        super(message);
    }

    public InvalidDropInFileException(Throwable cause) {
        super(cause);
    }
}
