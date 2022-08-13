package de.utopiamc.framework.common.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import de.utopiamc.framework.common.bootstrap.inject.Bootstrapper;
import de.utopiamc.framework.common.bootstrap.inject.FrameworkBootstrapModule;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.common.dropin.DropIn;
import de.utopiamc.framework.common.service.DropInFileService;
import de.utopiamc.framework.common.service.IndexService;
import de.utopiamc.framework.common.service.impl.DropInBootstrapService;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BootstrapContext implements Bootstrapper {

    private Injector injector;

    private final Set<Module> modules;
    private final List<Module> prodModules;

    public BootstrapContext() {
        this.modules = new HashSet<>();
        this.prodModules = new LinkedList<>();

        setupBootstrapModules();
    }

    private void setupBootstrapModules() {
        modules.add(new FrameworkBootstrapModule());
    }

    @Override
    public Injector getBootstrapInjector() {
        if (injector == null)
            injector = Guice.createInjector(modules);

        return injector;
    }

    @Override
    public ApplicationContext createApplicationContext() {
        Set<DropIn> dropIns = loadDropIns();

        return new ApplicationContext(dropIns, prodModules);
    }

    private Set<DropIn> loadDropIns() {
        DropInFileService dropInFileService = getBootstrapInjector().getInstance(DropInFileService.class);
        IndexService indexService = getBootstrapInjector().getInstance(IndexService.class);
        DropInBootstrapService dropInBootstrapService = getBootstrapInjector().getInstance(DropInBootstrapService.class);

        return dropInBootstrapService.bootstrapFromJarFileIndices(
                indexService.indexJarFiles(
                        dropInFileService.getDropInFiles()));
    }


    public void addModule(Module module) {
        modules.add(module);
    }
    public void addProdModule(Module module) {
        prodModules.add(module);
    }

}
