package de.utopiamc.framework.api.module;

import com.google.inject.Inject;
import de.utopiamc.framework.api.dropin.DropInService;
import de.utopiamc.framework.api.dropin.inject.StereotypeResolver;

import java.lang.annotation.Annotation;

public abstract class AbstractDropInModule {

    @Inject
    protected DropInService dropInService;

    public abstract void configure();

    protected DropInService dropInService() {
        return dropInService;
    }

    protected void addStereotype(Class<? extends Annotation> identifier, StereotypeResolver resolver) {
        dropInService.addStereotype(identifier, resolver);
    }

}
