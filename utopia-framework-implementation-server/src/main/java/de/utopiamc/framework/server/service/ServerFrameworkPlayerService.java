package de.utopiamc.framework.server.service;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import org.bukkit.Bukkit;

import java.util.UUID;

public class ServerFrameworkPlayerService implements FrameworkPlayerService {

    @Override
    public FrameworkPlayer get(UUID uuid) {
        return new ServerFrameworkPlayer(Bukkit.getPlayer(uuid));
    }

    @Override
    public FrameworkPlayer get(String name) {
        return new ServerFrameworkPlayer(Bukkit.getPlayer(name));
    }
}
