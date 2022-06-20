package de.utopiamc.framework.server.plugin;

import de.utopiamc.framework.api.event.LifecycleEvent;
import de.utopiamc.framework.api.event.OnDisableEvent;
import de.utopiamc.framework.api.event.OnEnableEvent;
import de.utopiamc.framework.api.event.OnLoadEvent;
import de.utopiamc.framework.common.bootstrap.BootstrapContext;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.server.bootstrap.ServerBootstrapModule;
import org.bukkit.plugin.java.JavaPlugin;

public class UtopiaFrameworkPlugin extends JavaPlugin {

    private boolean isBootstrapped = false;
    private ApplicationContext applicationContext;

    @Override
    public void onLoad() {
        bootstrap();

        callSecureLifecycleEvent(new OnLoadEvent());
    }

    @Override
    public void onEnable() {
        bootstrap();

        callSecureLifecycleEvent(new OnEnableEvent());
    }

    @Override
    public void onDisable() {
        callSecureLifecycleEvent(new OnDisableEvent());
        applicationContext.disable();

        isBootstrapped = false;

    }

    private void bootstrap() {
        if (!isBootstrapped) {
            BootstrapContext bootstrapContext = new BootstrapContext();
            bootstrapContext.addModule(new ServerBootstrapModule());

            applicationContext = bootstrapContext.createApplicationContext();

            isBootstrapped = true;
        }
    }

    private void callSecureLifecycleEvent(LifecycleEvent event) {
        if (applicationContext != null)
            applicationContext.dispatchEvent(event);
    }

}
