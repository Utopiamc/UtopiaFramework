package de.utopiamc.framework.proxy.service;

import com.google.inject.Inject;
import de.utopiamc.framework.common.exceptions.InvalidDropInFileException;
import de.utopiamc.framework.common.service.DropInFactoryService;
import de.utopiamc.framework.common.service.DropInFileService;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ProxyDropInFileService implements DropInFileService {

    @Inject
    private Logger logger;

    @Override
    public Set<File> getDropInFiles() {
        String path = DROP_IN_DIRECTORY;
        File file = new File(path);

        if (!file.exists()) {
            file.mkdir();
        }

        if (!file.isDirectory()) {
            throw new InvalidDropInFileException(String.format(NOT_A_DIRECTORY, path));
        }

        Set<File> collect = Arrays.stream(file.listFiles())
                .filter(f -> f.getName().endsWith(DropInFactoryService.JAR_FILE_EXTENSION))
                .collect(Collectors.toSet());

        logger.log(Level.INFO, String.format("Found %s possible drop-ins in '%s'.", collect.size(), path));

        return collect;
    }

}
