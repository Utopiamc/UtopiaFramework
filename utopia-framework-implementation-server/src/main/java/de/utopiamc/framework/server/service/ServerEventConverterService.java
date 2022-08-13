package de.utopiamc.framework.server.service;

import com.google.inject.Inject;
import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.event.WrappedFrameworkEvent;
import de.utopiamc.framework.api.events.BukkitEvent;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.common.service.EventConverterService;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;

public class ServerEventConverterService implements EventConverterService {

    private final FrameworkPlayerService playerService;

    @Inject
    public ServerEventConverterService(FrameworkPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public FrameworkEvent convertEvent(Object o) {
        Class<?> oClass = o.getClass();
        if (Event.class.isAssignableFrom(o.getClass())) {
            return convertBukkitEvent((Event) o, oClass);
        }

        return new WrappedFrameworkEvent(o);
    }

    private BukkitEvent convertBukkitEvent(Event o, Class<?> oClass) {
        if (oClass.isAssignableFrom(PlayerEvent.class)) {
            return new de.utopiamc.framework.api.events.player.PlayerEvent((PlayerEvent) o, ((ServerFrameworkPlayer) playerService.get(((PlayerEvent) o).getPlayer().getUniqueId())));
        }
        if (oClass.isAssignableFrom(EntityEvent.class)) {
            return new de.utopiamc.framework.api.events.entity.EntityEvent((EntityEvent) o);
        }
        return new BukkitEvent(o);
    }
}
