package de.utopiamc.framework.common.service;

import java.io.File;
import java.util.Set;

public interface DropInFileService {

    String DROP_IN_DIRECTORY = "drop-ins";
    String NOT_A_DIRECTORY = "'%s' is not a directory!";

    Set<File> getDropInFiles();

}
