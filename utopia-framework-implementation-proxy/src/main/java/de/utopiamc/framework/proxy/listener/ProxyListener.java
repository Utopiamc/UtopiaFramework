package de.utopiamc.framework.proxy.listener;

import de.utopiamc.framework.api.service.PlayerHandler;
import de.utopiamc.framework.common.context.ApplicationContext;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.inject.Inject;

public class ProxyListener implements Listener {

    private final ApplicationContext context;
    private final PlayerHandler playerHandler;

    @Inject
    public ProxyListener(ApplicationContext context, PlayerHandler playerHandler) {
        this.context = context;
        this.playerHandler = playerHandler;
    }

    @EventHandler
    public void onEvent(Event event) {
        context.dispatchEvent(event);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        playerHandler.quit(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        playerHandler.join(event.getPlayer().getUniqueId());
    }

}
