package de.utopiamc.framework.api.service;

import java.util.UUID;

public interface PlayerHandler {

    void join(UUID uuid);

    void quit(UUID uuid);

}
