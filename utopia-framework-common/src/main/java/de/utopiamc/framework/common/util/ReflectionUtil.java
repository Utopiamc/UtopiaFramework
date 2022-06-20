package de.utopiamc.framework.common.util;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class ReflectionUtil {

    @SuppressWarnings("uncheked")
    public static <T> Set<Class<? extends T>> getInterfaces(Class<?> cls, Class<T> interfaceToExtend) {
        Set<Class<? extends T>> interfaces = new HashSet<>();

        for (Class<?> anInterface : cls.getInterfaces()) {
            if (interfaceToExtend.isAssignableFrom(anInterface))
                interfaces.add((Class<? extends T>) anInterface);
        }

        return interfaces;
    }

}
