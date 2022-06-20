package de.utopiamc.framework.api.event;

public class OnDisableEvent extends LifecycleEvent {

    @Override
    public Class<?> getCompareableClass() {
        return getClass();
    }
}
