package de.utopiamc.framework.api.tasks;

public interface TaskService {

    Task runSync(Runnable task);
    Task runSync(Runnable task, long delay);

    Task runAsync(Runnable task);

    Task repeat(Runnable task, long period);
    Task repeat(Runnable task, long period, long delay);

}
