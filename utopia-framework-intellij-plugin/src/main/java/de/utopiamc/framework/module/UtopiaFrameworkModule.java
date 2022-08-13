package de.utopiamc.framework.module;

import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class UtopiaFrameworkModule extends ModuleType<UtopiaFrameworkModuleBuilder> {

    private static final String ID = "UTOPIA-FRAMEWORK_MODULE";

    protected UtopiaFrameworkModule() {
        super(ID);
    }

    @NotNull
    @Override
    public UtopiaFrameworkModuleBuilder createModuleBuilder() {
        return null;
    }

    @NotNull
    @Override
    @Nls(capitalization = Nls.Capitalization.Title)
    public String getName() {
        return null;
    }

    @NotNull
    @Override
    @Nls(capitalization = Nls.Capitalization.Sentence)
    public String getDescription() {
        return null;
    }

    @NotNull
    @Override
    public Icon getNodeIcon(boolean isOpened) {
        return null;
    }

}
