package de.utopiamc.framework.common.dropin;

import com.google.gson.annotations.SerializedName;
import de.utopiamc.framework.common.dropin.manifest.ManifestDropInSource;
import de.utopiamc.framework.common.dropin.manifest.ManifestGlobals;
import de.utopiamc.framework.common.exceptions.MalformedManifestException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class DropInManifest {

    @SerializedName(value = "version", alternate = {"manifestVersion", "man-version"})
    private String manifestVersion;
    private ManifestGlobals global;

    @SerializedName(value = "sources", alternate = {"dropin", "dropins", "plugin", "plugins"})
    private List<ManifestDropInSource> sources;

    public DropInManifest validated() {
        validate();
        return this;
    }

    private void validate() {
        if (manifestVersion == null)
            throw new MalformedManifestException("The manifest version is null, but it is required!");
        global.validate(this);

        if (sources == null || sources.isEmpty())
            throw new MalformedManifestException("The is no source provided, but you need at least one source to bootstrap your dropin.");

        sources.forEach(source -> source.validate(this));
    }
}
