package de.utopiamc.framework.server.service;

import de.utopiamc.framework.common.exceptions.InvalidDropInFileException;
import de.utopiamc.framework.common.service.DropInFactoryService;
import de.utopiamc.framework.common.service.DropInFileService;
import de.utopiamc.framework.server.plugin.UtopiaFrameworkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerDropInFileService implements DropInFileService {

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

        return Arrays.stream(file.listFiles())
                .filter(f -> f.getName().endsWith(DropInFactoryService.JAR_FILE_EXTENSION))
                .collect(Collectors.toSet());
    }

}
