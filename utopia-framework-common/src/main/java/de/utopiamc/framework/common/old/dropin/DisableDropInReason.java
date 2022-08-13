package de.utopiamc.framework.common.old.dropin;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum DisableDropInReason {
    REQUIRED_DEPENDENCY_NOT_FOUND(Level.SEVERE);

    private String msg;
    private final Level level;

    DisableDropInReason(Level level) {
        this.level = level;
    }

    public DisableDropInReason setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public void log(Logger logger) {
        if (msg != null)
            logger.log(level, msg);
    }
}
