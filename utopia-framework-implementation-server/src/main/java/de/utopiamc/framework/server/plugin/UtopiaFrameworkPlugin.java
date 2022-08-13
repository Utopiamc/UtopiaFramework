package de.utopiamc.framework.server.plugin;

import de.utopiamc.framework.api.event.lifecycle.LifecycleEvent;
import de.utopiamc.framework.api.event.lifecycle.OnDisableEvent;
import de.utopiamc.framework.api.event.lifecycle.OnEnableEvent;
import de.utopiamc.framework.api.event.lifecycle.OnLoadEvent;
import de.utopiamc.framework.common.bootstrap.BootstrapContext;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.server.bootstrap.ServerBootstrapModule;
import de.utopiamc.framework.server.inject.ServerModule;
import de.utopiamc.framework.server.listener.ServerListener;
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
        System.out.println("HI");
        bootstrap();

        callSecureLifecycleEvent(new OnEnableEvent());
        registerEventListener();
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
            bootstrapContext.addProdModule(new ServerModule());

            applicationContext = bootstrapContext.createApplicationContext();

            isBootstrapped = true;
        }
    }

    private void callSecureLifecycleEvent(LifecycleEvent event) {
        if (applicationContext != null)
            applicationContext.dispatchEvent(event);
    }

    private void registerEventListener() {
        try {
            if (applicationContext == null) return;
            ServerListener instance = applicationContext.getGuiceInjector().getInstance(ServerListener.class);
            instance.register(this);
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
