package de.utopiamc.framework.intellij.creator;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;

public class ModuleCreator extends ModuleBuilder {

    @Override
    public ModuleType<?> getModuleType() {
        return ModuleType.EMPTY;
    }

}
