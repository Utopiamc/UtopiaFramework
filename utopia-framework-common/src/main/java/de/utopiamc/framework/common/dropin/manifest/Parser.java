package de.utopiamc.framework.common.dropin.manifest;

import de.utopiamc.framework.common.dropin.DropInManifest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface Parser {

    DropInManifest parse(InputStream file) throws IOException;

}
