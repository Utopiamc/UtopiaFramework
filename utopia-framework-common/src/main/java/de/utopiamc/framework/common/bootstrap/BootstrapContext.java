package de.utopiamc.framework.common.bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import de.utopiamc.framework.common.bootstrap.inject.Bootstrapper;
import de.utopiamc.framework.common.bootstrap.inject.FrameworkBootstrapModule;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.common.dropin.DropIn;
import de.utopiamc.framework.common.service.DropInFactoryService;
import de.utopiamc.framework.common.service.DropInFileService;

import java.util.HashSet;
import java.util.Set;

public class BootstrapContext implements Bootstrapper {

    private Injector injector;

    private final Set<Module> modules;

    public BootstrapContext() {
        this.modules = new HashSet<>();

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
        DropInFileService fileService = getBootstrapInjector().getInstance(DropInFileService.class);
        DropInFactoryService factoryService = getBootstrapInjector().getInstance(DropInFactoryService.class);

        Set<DropIn> dropIns = factoryService.makeDropIns(fileService.getDropInFiles());

        return new ApplicationContext(dropIns);
    }

    public void addModule(Module module) {
        modules.add(module);
    }

}
