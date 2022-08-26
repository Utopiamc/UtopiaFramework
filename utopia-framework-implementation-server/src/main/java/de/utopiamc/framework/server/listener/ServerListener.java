package de.utopiamc.framework.server.listener;

import de.utopiamc.framework.api.service.PlayerHandler;
import de.utopiamc.framework.common.context.ApplicationContext;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerListener implements Listener {

    @Inject
    private PlayerHandler playerHandler;

    @Inject
    private ApplicationContext applicationContext;

    public void register(Plugin plugin) {
        Set<Class<? extends Event>> eventList = new Reflections("org.bukkit")
                .getSubTypesOf(Event.class).stream()
                .filter(clazz -> Arrays.stream(clazz.getDeclaredFields()).anyMatch(field -> field.getType().getName().endsWith("HandlerList")))
                .collect(Collectors.toSet());

        System.out.println("Scanned Events: " + eventList.size());

        EventExecutor eventExecutor = ((listener, event) -> eventHandler(event));
        for (Class<? extends Event> aClass : eventList) {
            plugin.getServer().getPluginManager().registerEvent(aClass, this, EventPriority.MONITOR, eventExecutor, plugin);
        }
    }

    private void eventHandler(Event event) {
        applicationContext.dispatchEvent(event);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerHandler.join(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerHandler.quit(event.getPlayer().getUniqueId());
    }

}
