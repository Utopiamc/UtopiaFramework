package de.utopiamc.framework.common.context;

import com.google.inject.*;
import com.google.inject.Module;
import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.common.config.CommonConfigurationModule;
import de.utopiamc.framework.common.dropin.DisableDropInReason;
import de.utopiamc.framework.common.dropin.DropIn;
import de.utopiamc.framework.common.dropin.DropInSource;
import de.utopiamc.framework.common.dropin.manifest.ManifestDependency;
import de.utopiamc.framework.common.events.EventDispatchable;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.common.inject.FrameworkModule;
import de.utopiamc.framework.common.util.EventUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ApplicationContext implements Context, DropInHoldable, EventDispatchable {

    @Inject
    private static Logger logger;

    private final Set<DropIn> dropIns;
    private final Injector guiceInjector;

    public ApplicationContext(Set<DropIn> dropIns) {
        this.dropIns = dropIns;

        // Post Setup DropIns
        validateDropInDependencies();

        // Setup Guice
        Set<Module> modules = generateModules();
        guiceInjector = createInjector(modules);
    }

    public void disable() {

    }

    //region DropIn Stuff
    private void validateDropInDependencies() {
        for (DropInSource dropInSource : getDropInSources()) {
            for (ManifestDependency dependency : dropInSource.getSource().getDependencies()) {
                if (dependency.getRequired() && getById(dependency.getId()).isEmpty())
                    disableDropIn(dropInSource, DisableDropInReason.REQUIRED_DEPENDENCY_NOT_FOUND.setMsg(String.format("Required dependency '%s' for '%s' not found. Disable dropin source.", dependency.getId(), dropInSource.getId())));
            }
        }
    }

    private void disableDropIn(DropInSource dropInSource, DisableDropInReason reason) {
        dropInSource.disable();

        reason.log(logger);
    }
    //endregion

    //region Guice Stuff
    private Set<Module> generateModules() {
        Set<Module> modules = new HashSet<>();

        modules.add(new FrameworkModule(this));
        modules.add(new CommonConfigurationModule());

        dropIns.forEach(d -> modules.add(d.createDropInModule()));

        return modules;
    }

    private Injector createInjector(Set<Module> modules) {
        return Guice.createInjector(modules);
    }
    //endregion

    //region DropInHoldable Implementation
    @Override
    public Optional<DropInSource> getById(String id) {
        return dropIns.stream()
                .flatMap(d -> d.getSources().stream())
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    @Override
    public Set<DropIn> getDropIns() {
        return dropIns;
    }

    @Override
    public Set<DropInSource> getDropInSources() {
        return dropIns.stream()
                .flatMap(d -> d.getSources().stream())
                .collect(Collectors.toSet());
    }
    //endregion

    //region EventDispatchable Implementation
    public void dispatchEvent(Object event) {
        dispatchEvent(EventUtil.convertToFrameworkEvent(event));
    }

    @Override
    public void dispatchEvent(FrameworkEvent event) {
        dropIns.forEach(d -> d.dispatchEvent(event, this));
    }
    //endregion

    public Injector getGuiceInjector() {
        return guiceInjector;
    }
}
