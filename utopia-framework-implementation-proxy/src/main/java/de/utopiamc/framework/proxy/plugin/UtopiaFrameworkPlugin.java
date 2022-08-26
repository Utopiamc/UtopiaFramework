package de.utopiamc.framework.proxy.plugin;

import de.utopiamc.framework.api.event.lifecycle.LifecycleEvent;
import de.utopiamc.framework.api.event.lifecycle.OnDisableEvent;
import de.utopiamc.framework.api.event.lifecycle.OnEnableEvent;
import de.utopiamc.framework.api.event.lifecycle.OnLoadEvent;
import de.utopiamc.framework.common.bootstrap.BootstrapContext;
import de.utopiamc.framework.common.context.ApplicationContext;
import de.utopiamc.framework.proxy.inject.ProxyBootstrapModule;
import de.utopiamc.framework.proxy.inject.ProxyModule;
import de.utopiamc.framework.proxy.listener.ProxyListener;
import net.md_5.bungee.api.plugin.Plugin;

public class UtopiaFrameworkPlugin extends Plugin {

    private static UtopiaFrameworkPlugin instance;

    public static UtopiaFrameworkPlugin getInstance() {
        return instance;
    }

    private boolean isBootstrapped = false;
    private ApplicationContext applicationContext;

    @Override
    public void onLoad() {
        instance = this;

        bootstrap();

        callSecureLifecycleEvent(new OnLoadEvent());
    }

    @Override
    public void onEnable() {
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
            bootstrapContext.addModule(new ProxyBootstrapModule());
            bootstrapContext.addProdModule(new ProxyModule());

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
            ProxyListener instance = applicationContext.getGuiceInjector().getInstance(ProxyListener.class);
            getProxy().getPluginManager().registerListener(this, instance);
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
