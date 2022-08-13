package de.utopiamc.framework.common.service.impl;

import de.utopiamc.framework.common.old.dropin.DropIn;
import de.utopiamc.framework.common.exceptions.InvalidDropInFileException;
import de.utopiamc.framework.common.service.DropInFactoryService;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CommonDropInFactoryService implements DropInFactoryService {

    @Inject
    private Logger logger;

    @Override
    public Set<DropIn> makeDropIns(Set<File> files) {
        return files.stream()
                .map(this::makeDropIn)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private DropIn makeDropIn(File file) {
        try {
            if (!file.getName().endsWith(JAR_FILE_EXTENSION))
                throw new InvalidDropInFileException("Invalid drop in file format. There are only JAR_FILES allowed.");

            try {
                ClassLoader cl = new URLClassLoader(new URL[]{new URL("jar:file:" + file.getAbsolutePath() + "!/")}, getClass().getClassLoader());
                JarFile jarFile = new JarFile(file);

                return new DropIn(cl, jarFile);
            } catch (IOException e) {
                throw new InvalidDropInFileException(e);
            }
        } catch (Throwable throwable) {
            System.err.printf("Failed to enable DropIn '%s', The error was: ", file.getName());
            throwable.printStackTrace();
        }
        return null;
    }
}
