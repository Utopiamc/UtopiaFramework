package de.utopiamc.framework.common.dropin.sourcetypes;

import de.utopiamc.framework.common.dropin.DropInModule;

public abstract class SourceType {

    protected final Class<?> type;

    public SourceType(Class<?> type) {
        this.type = type;
    }

    public abstract void bind(DropInModule module);

}
