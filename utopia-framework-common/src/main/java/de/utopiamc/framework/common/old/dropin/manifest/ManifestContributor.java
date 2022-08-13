package de.utopiamc.framework.common.old.dropin.manifest;

import de.utopiamc.framework.common.exceptions.MalformedManifestException;
import lombok.ToString;

@ToString
public final class ManifestContributor {
    private final String name;
    private final String description;

    public ManifestContributor(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void validate() {
        if (name == null)
            throw new MalformedManifestException("The name on one Contributor is null, but it is required.");
    }
}
