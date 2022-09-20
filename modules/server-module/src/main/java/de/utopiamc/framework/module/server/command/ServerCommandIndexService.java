package de.utopiamc.framework.module.server.command;

import com.google.inject.Inject;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.service.CommandIndexService;
import de.utopiamc.framework.api.stereotype.Command;
import de.utopiamc.framework.api.stereotype.Service;
import de.utopiamc.framework.module.server.command.context.CommandContext;

import java.util.logging.Logger;

@Service
public class ServerCommandIndexService implements CommandIndexService {

    private final Context context;
    private final UtopiaMetaConfig metaConfig;

    @Inject
    public ServerCommandIndexService(Context context, UtopiaMetaConfig metaConfig) {
        this.context = context;
        this.metaConfig = metaConfig;
    }

    @Override
    public void indexCommand(Command commandInfo, Class<?> commandClass) {
        try {
            CommandContext commandContext = new CommandContext(context.getInstance(commandClass), commandInfo, metaConfig);
        }catch (Throwable e) {
            Logger.getLogger("CMD: " + commandInfo.value()).severe("Error indexing command %s. %s".formatted(commandInfo.value(), e.getMessage()));
            e.printStackTrace();
        }
    }



}