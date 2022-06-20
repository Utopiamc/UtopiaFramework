package de.utopiamc.framework.common.service.impl;

import de.utopiamc.framework.common.dropin.DropInManifest;
import de.utopiamc.framework.common.dropin.manifest.JSONManifestParser;
import de.utopiamc.framework.common.dropin.manifest.Parser;
import de.utopiamc.framework.common.service.ManifestParserService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CommonManifestParserService implements ManifestParserService {

    private static final String XML_FILE_EXTENSION = ".xml";
    private static final String JSON_FILE_EXTENSION = ".json";

    @Override
    public DropInManifest parseManifest(InputStream file) {
        try {
            return new JSONManifestParser().parse(file);
        } catch (IOException e) {
            // TODO: create exception
            throw new RuntimeException(e);
        }
    }



}
