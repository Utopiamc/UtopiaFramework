package de.utopiamc.framework.common.service;

import com.google.inject.ImplementedBy;
import de.utopiamc.framework.common.models.JarFileIndex;
import de.utopiamc.framework.common.service.impl.CommonIndexService;

import java.io.File;
import java.util.Set;

@ImplementedBy(CommonIndexService.class)
public interface IndexService {

    Set<JarFileIndex> indexJarFiles(Set<File> jarFiles);

}
