package de.utopiamc.framework.server.service;

import de.utopiamc.framework.api.service.AsyncService;
import de.utopiamc.framework.server.plugin.UtopiaFrameworkPlugin;
import org.bukkit.Bukkit;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ServerAsyncService implements AsyncService {

    @Override
    public void doAsync(Consumer<Runnable> tasks, Runnable afterSyncTask) {
        Bukkit.getScheduler().runTaskAsynchronously(UtopiaFrameworkPlugin.getInstance(), () -> tasks.accept(() -> Bukkit.getScheduler().runTask(UtopiaFrameworkPlugin.getInstance(), afterSyncTask)));
    }

    @Override
    public <T> void doAsync(Supplier<T> task, Consumer<T> syncAfterTask) {
        Bukkit.getScheduler().runTaskAsynchronously(UtopiaFrameworkPlugin.getInstance(), () -> {
            T t = task.get();
            Bukkit.getScheduler().runTask(UtopiaFrameworkPlugin.getInstance(), () -> {
                syncAfterTask.accept(t);
            });
        });
    }
}
