package de.utopiamc.framework.common.service.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.utopiamc.framework.api.dropin.DropInService;
import de.utopiamc.framework.api.dropin.inject.ClassDetails;
import de.utopiamc.framework.api.module.AbstractDropInModule;
import de.utopiamc.framework.common.dropin.CommonDropInModule;
import de.utopiamc.framework.common.dropin.DropIn;
import de.utopiamc.framework.common.exceptions.InvalidDropInModuleClassException;
import de.utopiamc.framework.common.models.JarFileIndex;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CommonDropInBootstrapService implements DropInBootstrapService {

    @Inject
    private Logger logger;

    @Inject
    private DropInService dropInService;

    @Override
    public Set<DropIn> bootstrapFromJarFileIndices(Set<JarFileIndex> jarFileIndices) {
        Map<ClassLoader, List<String>> modules = jarFileIndices.stream()
                .collect(Collectors.toMap(JarFileIndex::loader,
                        jarFileIndex -> jarFileIndex.config().getModules(),
                        (a, b) -> b));
        Set<AbstractDropInModule> dropInModules = createDropInModules(extractDropInModules(modules));
        System.out.println(dropInModules);
        dropInModules.forEach(AbstractDropInModule::configure);

        return jarFileIndices.stream()
                .map(this::bootstrapDropIn)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private DropIn bootstrapDropIn(JarFileIndex index) {
        try {
            Set<ClassDetails> classDetails = dropInService.scanClasses(index.classes());
            index.loader().close();
            return new DropIn(classDetails, index);
        }catch (Throwable throwable) {
            logger.warning(String.format("Failed to bootstrap drop-in '%s'! %s", index.name(), throwable.getMessage()));
            throwable.printStackTrace();
        }
        return null;
    }

    private Set<Class<? extends AbstractDropInModule>> extractDropInModules(Map<ClassLoader, List<String>> modules) {
         return modules.entrySet().stream()
                 .map(this::extractDropInModulesFromEntry)
                 .filter(Objects::nonNull)
                 .flatMap(Collection::stream)
                 .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private Set<Class<? extends AbstractDropInModule>> extractDropInModulesFromEntry(Map.Entry<ClassLoader, List<String>> modules) {
        try {
            Set<Class<? extends AbstractDropInModule>> moduleClasses = new HashSet<>();
            for (String s : modules.getValue()) {
                try {
                    Class<?> aClass = modules.getKey().loadClass(s);
                    if (!AbstractDropInModule.class.isAssignableFrom(aClass)) {
                        throw new InvalidDropInModuleClassException(String.format("Invalid DropInModule, class '%s' does not extend '%s'", aClass.getName(), AbstractDropInModule.class.getName()));
                    }
                    moduleClasses.add((Class<? extends AbstractDropInModule>) aClass);
                } catch (ClassNotFoundException e) {
                    throw new InvalidDropInModuleClassException(String.format("Invalid DropInModule, class '%s' not found.", s));
                }
            }
            return moduleClasses;
        }catch (Throwable t) {
            logger.warning("Failed to load drop-in module!");
            t.printStackTrace();
        }
        return null;
    }


    @Override
    public Set<AbstractDropInModule> createDropInModules(Set<Class<? extends AbstractDropInModule>> dropInModules) {
        System.out.println(dropInService);
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(DropInService.class).toInstance(dropInService);
            }
        });

        Set<AbstractDropInModule> collect = dropInModules.stream()
                .map(m -> createModule(m, injector))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        collect.add(new CommonDropInModule(dropInService));
        return collect;
    }

    private AbstractDropInModule createModule(Class<? extends AbstractDropInModule> cls, Injector injector) {
        try {
            return injector.getInstance(cls);
        }catch (Throwable t) {
            logger.warning(String.format("Failed to create module '%s'.", cls.getName()));
            t.printStackTrace();
        }
        return null;
    }

}
