package de.utopiamc.framework.common.checks;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Assert {

    public static void assertTrue(boolean bool, String message) {
        if (!bool)
            throw new RuntimeException(message);
    }

    public static void assertNonNull(Object o, String message) {
        if (o == null)
            throw new NullPointerException(message);
    }

}
