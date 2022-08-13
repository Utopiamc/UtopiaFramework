package de.utopiamc.framework.common.old.dropin;

import com.google.inject.AbstractModule;

public class DropInModule extends AbstractModule {

    private final DropIn dropIn;

    public DropInModule(DropIn dropIn) {
        this.dropIn = dropIn;
    }

    @Override
    protected void configure() {
        dropIn.configure(binder());
    }
}
