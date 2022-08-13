package de.utopiamc.framework.common.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JarFileConfig {

    private List<String> modules;
    private List<String> searchPoints;

    public JarFileConfig() {
        modules = new ArrayList<>();
        searchPoints = new ArrayList<>();
    }


}
