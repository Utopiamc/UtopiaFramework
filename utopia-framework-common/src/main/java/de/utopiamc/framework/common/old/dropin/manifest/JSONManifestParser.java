package de.utopiamc.framework.common.old.dropin.manifest;

import com.google.gson.Gson;
import de.utopiamc.framework.common.old.dropin.DropInManifest;

import java.io.*;

public class JSONManifestParser implements Parser {

    @Override
    public DropInManifest parse(InputStream file) throws IOException {
        Gson gson = new Gson();

        return gson.fromJson(new InputStreamReader(file), DropInManifest.class).validated();
    }
}
    