package de.utopiamc.framework.server.service;

import com.google.inject.Inject;
import de.utopiamc.framework.api.entity.ServerFrameworkPlayer;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.event.WrappedFrameworkEvent;
import de.utopiamc.framework.api.events.BukkitEvent;
import de.utopiamc.framework.api.events.entity.FrameworkEntityEvent;
import de.utopiamc.framework.api.events.player.FrameworkPlayerEvent;
import de.utopiamc.framework.api.events.player.FrameworkPlayerJoinEvent;
import de.utopiamc.framework.api.events.player.FrameworkPlayerQuitEvent;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.common.service.EventConverterService;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        if (PlayerEvent.class.isAssignableFrom(oClass)) {
            if (PlayerJoinEvent.class.isAssignableFrom(oClass)) {
                return new FrameworkPlayerJoinEvent((PlayerJoinEvent) o, ((ServerFrameworkPlayer) playerService.get(((PlayerEvent) o).getPlayer().getUniqueId())));
            }
            if (PlayerQuitEvent.class.isAssignableFrom(oClass)) {
                return new FrameworkPlayerQuitEvent((PlayerQuitEvent) o, ((ServerFrameworkPlayer) playerService.get(((PlayerEvent) o).getPlayer().getUniqueId())));
            }
            return new FrameworkPlayerEvent((PlayerEvent) o, ((ServerFrameworkPlayer) playerService.get(((PlayerEvent) o).getPlayer().getUniqueId())));
        }
        if (EntityEvent.class.isAssignableFrom(oClass)) {
            return new FrameworkEntityEvent((EntityEvent) o);
        }
        return new BukkitEvent(o);
    }
}
