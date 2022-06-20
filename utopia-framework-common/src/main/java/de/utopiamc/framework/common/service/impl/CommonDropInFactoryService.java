package de.utopiamc.framework.common.service.impl;

import de.utopiamc.framework.common.dropin.DropIn;
import de.utopiamc.framework.common.dropin.DropInManifest;
import de.utopiamc.framework.common.exceptions.InvalidDropInFileException;
import de.utopiamc.framework.common.service.DropInFactoryService;
import de.utopiamc.framework.common.service.DropInFileService;
import de.utopiamc.framework.common.service.ManifestParserService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class CommonDropInFactoryService implements DropInFactoryService {

    private final ManifestParserService manifestParserService;

    @Inject
    public CommonDropInFactoryService(ManifestParserService manifestParserService) {
        this.manifestParserService = manifestParserService;
    }

    @Override
    public Set<DropIn> makeDropIns(Set<File> files) {
        return files.stream()
                .map(this::makeDropIn)
                .collect(Collectors.toSet());
    }

    private DropIn makeDropIn(File file) {
        if (!file.getName().endsWith(JAR_FILE_EXTENSION))
            throw new InvalidDropInFileException("Invalid dropin file format. Currently there are only JAR_FILES allowed. This will perhaps changed in a future release.");

        try {
            ClassLoader cl = new URLClassLoader(new URL[]{new URL("jar:file:" + file.getAbsolutePath() + "!/")}, getClass().getClassLoader());
            JarFile jarFile = new JarFile(file);
            InputStream resource = cl.getResourceAsStream("manifest.json");
            if (resource == null)
                throw new InvalidDropInFileException(String.format("There is no dropin manifest in '%s'.", file.getName()));

            DropInManifest manifest = manifestParserService.parseManifest(resource);

            DropIn dropIn = new DropIn(cl, jarFile, manifest);

            dropIn.make();

            return dropIn;
        } catch (IOException e) {
            throw new InvalidDropInFileException(e);
        }

    }
}
