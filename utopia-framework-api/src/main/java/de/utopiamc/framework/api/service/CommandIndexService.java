package de.utopiamc.framework.api.service;

import de.utopiamc.framework.api.stereotype.Command;

public interface CommandIndexService {

    void indexCommand(Command commandInfo, Class<?> commandClass);

}
