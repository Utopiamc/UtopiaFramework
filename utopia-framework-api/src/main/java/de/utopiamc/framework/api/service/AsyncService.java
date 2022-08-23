package de.utopiamc.framework.api.service;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface AsyncService {

    void doAsync(Consumer<Runnable> tasks, Runnable syncAfterTask);

    <T> void doAsync(Supplier<T> task, Consumer<T> syncAfterTask);

}
