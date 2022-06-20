package de.utopiamc.framework.common.dropin;

import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.common.dropin.manifest.ManifestDropInSource;
import de.utopiamc.framework.api.event.FrameworkEvent;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

@Data
public final class DropIn {

    @ToString.Exclude private final ClassLoader classLoader;
    @ToString.Exclude private final JarFile jarFile;
    private final DropInManifest manifest;

    private DropInModule dropInModule;
    private Set<DropInSource> sources;

    public DropIn(ClassLoader classLoader, JarFile jarFile, DropInManifest manifest) {
        this.classLoader = classLoader;
        this.jarFile = jarFile;
        this.manifest = manifest;

        this.sources = new HashSet<>();
    }


    public void make() {
        scanSources();
    }

    public DropInModule createDropInModule() {
        if (dropInModule == null)
            dropInModule = new DropInModule(this);

        return dropInModule;
    }

    private void scanSources() {
        manifest.getSources().forEach(this::scanSource);
    }

    private void scanSource(ManifestDropInSource manifestSource) {
        DropInSource source = new DropInSource(manifestSource, this);
        source.scan();

        sources.add(source);
    }

    public void bind(DropInModule module) {
        sources.forEach(s -> s.bind(module));
    }

    //region EventDispatchable Implementation
    public void dispatchEvent(FrameworkEvent event, ApplicationContext context) {
        if (dropInModule == null)
            throw new IllegalStateException();

        for (Method listener : dropInModule.getListeners(event.getCompareableClass())) {
            event.callMethod(listener, context.getGuiceInjector().getInstance(listener.getDeclaringClass()));
        }
    }

    //endregion
}
