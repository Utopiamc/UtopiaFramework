package de.utopiamc.framework.common.dropin.manifest;

import de.utopiamc.framework.common.dropin.DropInManifest;
import de.utopiamc.framework.common.exceptions.MalformedManifestException;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ToString
@Getter
public abstract class ShareableConfiguration {

    private String version;

    private Map<String, String> links;

    private List<ManifestContributor> contributors;
    private List<ManifestDependency> dependencies;

    public void validate(DropInManifest manifest) {
        if (!manifest.getGlobal().equals(this) && version == null) {
            String potentialVersion = manifest.getGlobal().getVersion();

            if (potentialVersion == null)
                throw new MalformedManifestException("Version is null, but it is required. This can happen, if you miss to specify a version for one source. But you can also set a global version and it will apply (when no other version is given) to all sources!");

            version = potentialVersion;
        }

        if (contributors == null)
            contributors = new ArrayList<>();

        if (dependencies == null)
            dependencies = new ArrayList<>();

        contributors.forEach(ManifestContributor::validate);
        dependencies.forEach(ManifestDependency::validate);

        if (!manifest.getGlobal().equals(this)) {
            contributors.addAll(manifest.getGlobal().getContributors());
            dependencies.addAll(manifest.getGlobal().getDependencies());
        }
    }
}
