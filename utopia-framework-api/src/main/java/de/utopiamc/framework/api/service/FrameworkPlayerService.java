package de.utopiamc.framework.api.service;

import de.utopiamc.framework.api.entity.FrameworkPlayer;

import java.util.UUID;

public interface FrameworkPlayerService {

    FrameworkPlayer get(UUID uuid);
    FrameworkPlayer get(String name);

}
