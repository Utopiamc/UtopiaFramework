package de.utopiamc.framework.common.old.dropin.sourcetypes;

import de.utopiamc.framework.common.old.dropin.DropInBinder;

public abstract class SourceType {

    protected final Class<?> type;

    public SourceType(Class<?> type) {
        this.type = type;
    }

    public abstract void bind(DropInBinder module);

}
