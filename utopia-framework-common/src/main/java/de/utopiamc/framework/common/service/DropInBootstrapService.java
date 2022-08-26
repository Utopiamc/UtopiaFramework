package de.utopiamc.framework.common.service;

import com.google.inject.ImplementedBy;
import de.utopiamc.framework.api.module.AbstractDropInModule;
import de.utopiamc.framework.common.dropin.DropIn;
import de.utopiamc.framework.common.models.JarFileIndex;
import de.utopiamc.framework.common.service.impl.CommonDropInBootstrapService;

import java.util.Set;

@ImplementedBy(CommonDropInBootstrapService.class)
public interface DropInBootstrapService {

    Set<DropIn> bootstrapFromJarFileIndices(Set<JarFileIndex> jarFileIndices);

    Set<AbstractDropInModule> createDropInModules(Set<Class<? extends AbstractDropInModule>> dropInModules);



}
