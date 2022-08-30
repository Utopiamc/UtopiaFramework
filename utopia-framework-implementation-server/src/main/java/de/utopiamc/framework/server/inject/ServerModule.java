package de.utopiamc.framework.server.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.service.AsyncService;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.tasks.TaskService;
import de.utopiamc.framework.common.service.EventConverterService;
import de.utopiamc.framework.server.plugin.UtopiaFrameworkPlugin;
import de.utopiamc.framework.server.service.ServerAsyncService;
import de.utopiamc.framework.server.service.ServerEventConverterService;
import de.utopiamc.framework.server.service.ServerFrameworkPlayerService;
import de.utopiamc.framework.server.service.ServerTaskService;
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
    }
}
