package de.utopiamc.framework.common.old.dropin.manifest;

import de.utopiamc.framework.common.old.dropin.DropInManifest;

import java.io.IOException;
import java.io.InputStream;

public interface Parser {

    DropInManifest parse(InputStream file) throws IOException;

}
