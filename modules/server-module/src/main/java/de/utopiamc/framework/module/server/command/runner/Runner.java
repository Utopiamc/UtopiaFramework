package de.utopiamc.framework.module.server.command.runner;

import de.utopiamc.framework.api.service.MessageService;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface Runner {

    void run(List<String> args, CommandSender sender, MessageService messageService, Object instance);

}
