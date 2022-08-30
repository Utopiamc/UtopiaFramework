package de.utopiamc.framework.server.service;

import com.google.inject.Inject;
import de.utopiamc.framework.api.tasks.Task;
import de.utopiamc.framework.api.tasks.TaskService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class ServerTaskService implements TaskService {

    private final JavaPlugin plugin;

    @Inject
    public ServerTaskService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Task runSync(Runnable task) {
        return runSync(task, 0);
    }

    @Override
    public Task runSync(Runnable task, long delay) {
        int id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task, delay);
        return () -> Bukkit.getScheduler().cancelTask(id);
    }

    @Override
    public Task runAsync(Runnable task) {
        BukkitTask id = Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
        return id::cancel;
    }

    @Override
    public Task repeat(Runnable task, long period) {
        return repeat(task, period, 0);
    }

    @Override
    public Task repeat(Runnable task, long period, long delay) {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, period, delay);
        return () -> Bukkit.getScheduler().cancelTask(id);
    }
}
