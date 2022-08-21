package de.utopiamc.framework.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Assertions {

    public static void assertNonNull(Object object) {
        assertNonNull(object, "");
    }

    public static void assertNonNull(Object object, String message) {
        if (object == null)
            throw new IllegalArgumentException(message);
    }

    public static void assertTrue(boolean condition) {
        assertTrue(condition, "");
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition)
            throw new IllegalArgumentException(message);
    }

    public static void assertFalse(boolean condition) {
        assertFalse(condition, "");
    }

    public static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

}
