package de.utopiamc.framework.api.event;

public class OnLoadEvent extends LifecycleEvent {

    @Override
    public Class<?> getCompareableClass() {
        return getClass();
    }
}
