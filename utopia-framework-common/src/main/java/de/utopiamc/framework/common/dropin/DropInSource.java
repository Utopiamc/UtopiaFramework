package de.utopiamc.framework.common.dropin;

import de.utopiamc.framework.common.dropin.manifest.ManifestDropInSource;
import de.utopiamc.framework.common.exceptions.DropInSourceLoaderException;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.common.util.ComponentUtil;
import de.utopiamc.framework.api.inject.annotations.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class DropInSource {

    @ToString.Exclude private final ManifestDropInSource source;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final DropIn dropIn;

    private Set<DropInSourceComponent> components;
    private Class<?> mainClass;

    public void scan() {
        try {
            mainClass = dropIn.getClassLoader().loadClass(source.getEntrypoint());
            String name = mainClass.getPackage().getName();

            components = scanForComponents(name);


        } catch (ClassNotFoundException e) {
            throw new DropInSourceLoaderException(String.format("Invalid entrypoint '%s' for '%s'. Class not found!", source.getEntrypoint(), source.getId()));
        }
    }

    public void bind(DropInModule module) {
        components.forEach(c -> c.bind(module));
    }

    private Set<DropInSourceComponent> scanForComponents(String root) {
        String path = root.replaceAll("\\.", File.separator);

        return dropIn.getJarFile().stream()
                .filter(entry -> entry.getName().startsWith(path) && entry.getName().endsWith(".class"))
                .map(entry -> entry.getName().replaceAll(File.separator, ".").replace(".class", ""))
                .map(this::loadClass)
                .filter(cls -> AnnotationUtil.isAnnotationPresent(cls, Component.class))
                .map(ComponentUtil::toSourceComponent)
                .collect(Collectors.toSet());
    }

    private Class<?> loadClass(String name) {
        try {
            return dropIn.getClassLoader().loadClass(name);
        } catch (Throwable e) {
            throw new DropInSourceLoaderException(String.format("Failed to load class '%s'.", name), e);
        }
    }

    public String getId() {
        return source.getId();
    }

    public void disable() {
        dropIn.getSources().remove(this);
    }
}
