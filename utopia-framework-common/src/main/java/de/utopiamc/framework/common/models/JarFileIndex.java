package de.utopiamc.framework.common.models;

import java.net.URLClassLoader;
import java.util.Set;

public record JarFileIndex(String name, URLClassLoader loader, Set<Class<?>> classes, JarFileConfig config) {


}
