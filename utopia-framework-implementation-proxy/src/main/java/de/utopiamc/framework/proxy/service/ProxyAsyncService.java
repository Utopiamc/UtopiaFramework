package de.utopiamc.framework.proxy.service;

import de.utopiamc.framework.api.service.AsyncService;
import de.utopiamc.framework.proxy.plugin.UtopiaFrameworkPlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProxyAsyncService implements AsyncService {

    @Override
    public void doAsync(Consumer<Runnable> tasks, Runnable syncAfterTask) {
        ProxyServer proxy = UtopiaFrameworkPlugin.getInstance().getProxy();
        TaskScheduler scheduler = proxy.getScheduler();
        scheduler.runAsync(UtopiaFrameworkPlugin.getInstance(), () -> {
           tasks.accept(syncAfterTask);
        });
    }

    @Override
    public <T> void doAsync(Supplier<T> task, Consumer<T> syncAfterTask) {
        ProxyServer proxy = UtopiaFrameworkPlugin.getInstance().getProxy();
        TaskScheduler scheduler = proxy.getScheduler();
        scheduler.runAsync(UtopiaFrameworkPlugin.getInstance(), () -> {
            syncAfterTask.accept(task.get());
        });
    }

}
