package de.utopiamc.framework.common.service;

import com.google.inject.ImplementedBy;
import de.utopiamc.framework.common.old.dropin.DropIn;
import de.utopiamc.framework.common.service.impl.CommonDropInFactoryService;

import java.io.File;
import java.util.Set;

@ImplementedBy(CommonDropInFactoryService.class)
public interface DropInFactoryService {

    String JAR_FILE_EXTENSION = ".jar";

    Set<DropIn> makeDropIns(Set<File> files);

}
