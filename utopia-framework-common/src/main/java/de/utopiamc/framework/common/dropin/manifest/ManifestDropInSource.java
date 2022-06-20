package de.utopiamc.framework.common.dropin.manifest;

import de.utopiamc.framework.common.dropin.DropInManifest;
import de.utopiamc.framework.common.exceptions.MalformedManifestException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManifestDropInSource extends ShareableConfiguration {

    private String id;
    private String name;
    private String entrypoint;
    private String description;

    @Override
    public void validate(DropInManifest manifest) {
        super.validate(manifest);

        if (id == null)
            throw new MalformedManifestException("The id of one source is null, but it is required to identify your dropin internal and to others.");
        if (entrypoint == null)
            throw new MalformedManifestException("The entrypoint of one source is null, but it is required to properly bootstrap your dropin.");

    }
}
