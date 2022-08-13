package de.utopiamc.framework.common.old.dropin;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.stereotype.Service;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.common.old.dropin.manifest.JSONManifestParser;
import de.utopiamc.framework.common.old.dropin.manifest.ManifestDropInSource;
import de.utopiamc.framework.common.events.EventSubscription;
import de.utopiamc.framework.common.exceptions.InvalidDropInFileException;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * <h1>DropIns</h1>
 *
 * <h2>How they work</h2>
 * <p>DropIns should be loaded from the {@code drop-ins} folder. They should be a {@link JarFile} containing a {@link DropInManifest}. A drop in should contain one module or plugin as well.</p>
 * <p>When the drop ins are loaded, the package from {@link ManifestDropInSource#getEntrypoint()} is used to scan all classes in the package and all following sub-packages. All classes annotated with {@link de.utopiamc.framework.api.inject.annotations.Component} will be created and injected with an {@link Injector}.</p>
 * <p>All Sources will be indexed by component annotations. There are some base components pre-build in the framework, but modules can extend these.</p>
 * <br>
 *
 * @author oskarwiedeweg
 */
@Data
public final class DropIn {

    /**
     * The {@link ClassLoader} used to load the classes from the drop in specific {@link DropIn#jarFile}.
     */
    @ToString.Exclude
    private final ClassLoader classLoader;

    /**
     * The {@link JarFile} containing all certain elements for the drop in.
     */
    @ToString.Exclude
    private final JarFile jarFile;

    /**
     * The {@link DropInManifest} contains all specified data about the drop in.
     */
    private final DropInManifest manifest;

    /**
     * A {@link DropInSource} is one class with specific features based on annotations like {@link Service}.
     */
    private Set<DropInSource> sources;

    public DropIn(ClassLoader classLoader, JarFile jarFile) throws IOException {
        this.classLoader = classLoader;
        this.jarFile = jarFile;


        this.manifest = loadManifest();
        this.sources = new HashSet<>();

        scanSources();
    }

    private DropInManifest loadManifest() throws IOException {
        InputStream resource = classLoader.getResourceAsStream("manifest.json");
        if (resource == null)
            throw new InvalidDropInFileException(String.format("There is no drop in manifest in '%s'.", jarFile.getName()));

        return new JSONManifestParser().parse(resource);
    }

    private void scanSources() {
        manifest.getSources().forEach(this::scanSource);
    }

    private void scanSource(ManifestDropInSource manifestSource) {
        DropInSource source = new DropInSource(manifestSource, this);
        source.scan();

        sources.add(source);
    }

    public void dispatchEvent(FrameworkEvent event, ApplicationContext context) {
        sources.forEach(s -> s.dispatchEvent(event, context));
    }

    public void configure(Binder moduleBinder) {
        DropInBinder binder = new DropInBinder() {
            @Override
            public Binder binder() {
                return moduleBinder;
            }

            @Override
            public void addEventSubscription(EventSubscription eventSubscription) {

            }
        };

        sources.forEach(s -> s.bind(binder));
    }

    public Module createDropInModule() {
        return new DropInModule(this);
    }
}
