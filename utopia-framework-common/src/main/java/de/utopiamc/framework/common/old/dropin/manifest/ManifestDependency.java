package de.utopiamc.framework.common.old.dropin.manifest;

import de.utopiamc.framework.common.exceptions.MalformedManifestException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class ManifestDependency {
    private final String id;
    private final String version;
    private Boolean required;

    public ManifestDependency(String id, String version, Boolean required) {
        this.id = id;
        this.version = version;
        this.required = required;
    }

    public void validate() {
        if (id == null)
            throw new MalformedManifestException("The id on one dependency is null, but it is required.");
        if (version == null)
            throw new MalformedManifestException("The version on one dependency is null, but it is required.");
        if (required == null)
            required = true;
    }
}
