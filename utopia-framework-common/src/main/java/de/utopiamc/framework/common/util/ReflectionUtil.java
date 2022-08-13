package de.utopiamc.framework.common.util;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class ReflectionUtil {

    @SuppressWarnings("unchecked")
    public static <T> Set<Class<? extends T>> getInterfaces(Class<?> cls, Class<T> interfaceToExtend) {
        Set<Class<? extends T>> interfaces = new HashSet<>();

        for (Class<?> anInterface : cls.getInterfaces()) {
            if (interfaceToExtend.isAssignableFrom(anInterface))
                interfaces.add((Class<? extends T>) anInterface);
        }

        return interfaces;
    }

    public static Set<Class<?>> getInterfaces(Class<?> cls) {
        Set<Class<?>> interfaces = new HashSet<>();

        getInterfaces(cls, interfaces);

        return interfaces;
    }

    private static void getInterfaces(Class<?> cls, Set<Class<?>> interfaces) {
        for (Class<?> anInterface : cls.getInterfaces()) {
            if (!interfaces.contains(anInterface)) {
                interfaces.add(anInterface);
                getInterfaces(anInterface, interfaces);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> getInterface(Class<?> cls, Class<T> interfaceClass) {
        for (Class<?> anInterface : cls.getInterfaces()) {
            if (interfaceClass.isAssignableFrom(cls)) {
                return (Class<? extends T>) anInterface;
            }
        }
        return null;
    }
}
