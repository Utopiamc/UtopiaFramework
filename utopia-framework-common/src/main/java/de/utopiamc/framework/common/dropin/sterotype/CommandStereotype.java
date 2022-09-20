package de.utopiamc.framework.common.dropin.sterotype;

import de.utopiamc.framework.api.dropin.DropInBinder;
import de.utopiamc.framework.api.dropin.inject.StereotypeResolver;
import de.utopiamc.framework.api.stereotype.Command;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.common.dropin.CommandRegisterTask;

public class CommandStereotype implements StereotypeResolver {

    @Override
    public void resolve(Class<?> cls, DropInBinder binder) {
        Command annotation = AnnotationUtil.getAnnotation(cls, Command.class);
        if (annotation != null)
            binder.registerTask(new CommandRegisterTask(cls, annotation));
    }

}
