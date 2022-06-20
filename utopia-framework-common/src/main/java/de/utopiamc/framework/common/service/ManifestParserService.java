package de.utopiamc.framework.common.service;

import com.google.inject.ImplementedBy;
import de.utopiamc.framework.common.dropin.DropInManifest;
import de.utopiamc.framework.common.service.impl.CommonManifestParserService;

import java.io.File;
import java.io.InputStream;

@ImplementedBy(CommonManifestParserService.class)
public interface ManifestParserService {

    DropInManifest parseManifest(InputStream file);

}
