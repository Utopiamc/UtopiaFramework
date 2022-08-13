package de.utopiamc.framework.common.old.dropin;

import com.google.inject.Binder;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.inject.annotations.Component;
import de.utopiamc.framework.api.stereotype.Plugin;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.common.old.dropin.manifest.ManifestDropInSource;
import de.utopiamc.framework.common.events.EventSubscription;
import de.utopiamc.framework.common.exceptions.DropInSourceLoaderException;
import de.utopiamc.framework.common.util.ComponentUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class DropInSource {

    /**
     * The {@link ManifestDropInSource} gives information about the specific source. It holds information like the {@code entrypoint} or the {@code dependencies}.
     */
    @ToString.Exclude
    private final ManifestDropInSource source;

    /**
     * The {@link DropIn} that contains this source.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final DropIn dropIn;

    /**
     * The {@link DropInSourceComponent}s in this source.
     */
    private Set<DropInSourceComponent> components;

    /**
     * The main class of the source. Usually it is annotated with {@link Plugin}
     */
    private Class<?> mainClass;

    private Set<EventSubscription> eventSubscriptions;

    public DropInSource(ManifestDropInSource source, DropIn dropIn) {
        this.source = source;
        this.dropIn = dropIn;

        this.eventSubscriptions = new HashSet<>();
    }

    public void scan() {
        try {
            mainClass = dropIn.getClassLoader().loadClass(source.getEntrypoint());
            String name = mainClass.getPackage().getName();

            components = scanForComponents(name);
        } catch (ClassNotFoundException e) {
            throw new DropInSourceLoaderException(String.format("Invalid entrypoint '%s' for '%s'. Class not found!", source.getEntrypoint(), source.getId()));
        }
    }

    public void bind(DropInBinder binder) {
        DropInBinder newBinder = new DropInBinder() {
            @Override
            public Binder binder() {
                return binder.binder();
            }

            @Override
            public void addEventSubscription(EventSubscription eventSubscription) {
                eventSubscriptions.add(eventSubscription);
            }
        };

        components.forEach(c -> c.bind(newBinder));
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

    public DropInSourceType getType() {
        return source.getType();
    }

    public void dispatchEvent(FrameworkEvent event, ApplicationContext context) {

    }
}
