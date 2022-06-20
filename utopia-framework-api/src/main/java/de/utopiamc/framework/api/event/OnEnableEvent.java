package de.utopiamc.framework.api.event;


public class OnEnableEvent extends LifecycleEvent {

    @Override
    public Class<?> getCompareableClass() {
        return getClass();
    }
}
