package de.utopiamc.framework.common.dropin;

import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.service.CommandIndexService;
import de.utopiamc.framework.api.stereotype.Command;

public class CommandRegisterTask implements Runnable {

    private Context context;
    private final Class<?> cmdClass;
    private final Command commandAnnotation;

    public CommandRegisterTask(Class<?> cmdClass, Command commandAnnotation) {
        this.cmdClass = cmdClass;
        this.commandAnnotation = commandAnnotation;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        System.out.println("Ja2");
        context.getInstance(CommandIndexService.class).indexCommand(commandAnnotation, cmdClass);
    }
}
