package de.utopiamc.test;

import de.utopiamc.framework.api.module.AbstractDropInModule;

public class Module extends AbstractDropInModule {

    @Override
    public void configure() {
        System.out.println("Ja");
        addStereotype(Cool.class, (cls, binder) -> System.out.println(cls));
    }
}
