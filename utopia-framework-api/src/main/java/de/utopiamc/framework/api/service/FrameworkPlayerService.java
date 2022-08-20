package de.utopiamc.framework.api.service;

import de.utopiamc.framework.api.entity.FrameworkPlayer;

import java.util.Date;
import java.util.UUID;

public interface FrameworkPlayerService {

    void cache(UUID uuid, String name, Date firstJoined);

    FrameworkPlayer get(UUID uuid);
    FrameworkPlayer get(String name);

}
