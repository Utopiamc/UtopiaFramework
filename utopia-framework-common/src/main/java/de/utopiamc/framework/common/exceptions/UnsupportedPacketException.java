package de.utopiamc.framework.common.exceptions;

public class UnsupportedPacketException extends Exception {

    public UnsupportedPacketException() {
    }

    public UnsupportedPacketException(String message) {
        super(message);
    }

    public UnsupportedPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedPacketException(Throwable cause) {
        super(cause);
    }
}
