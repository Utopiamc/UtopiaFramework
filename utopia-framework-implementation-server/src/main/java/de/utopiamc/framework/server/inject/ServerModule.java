package de.utopiamc.framework.server.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.service.AsyncService;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.PlayerHandler;
import de.utopiamc.framework.api.tasks.TaskService;
import de.utopiamc.framework.api.ui.scoreboard.Subscribeables;
import de.utopiamc.framework.common.service.EventConverterService;
import de.utopiamc.framework.server.plugin.UtopiaFrameworkPlugin;
import de.utopiamc.framework.server.service.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JavaPlugin.class).toInstance(UtopiaFrameworkPlugin.getInstance());
        bind(Plugin.class).toInstance(UtopiaFrameworkPlugin.getInstance());

        bind(EventConverterService.class).to(ServerEventConverterService.class);
        bind(FrameworkPlayerService.class).to(ServerFrameworkPlayerService.class);
        bind(AsyncService.class).to(ServerAsyncService.class);
        bind(TaskService.class).to(ServerTaskService.class);
        bind(PlayerHandler.class).to(ServerPlayerHandler.class);

        requestStaticInjection(Subscribeables.class);
    }
}
