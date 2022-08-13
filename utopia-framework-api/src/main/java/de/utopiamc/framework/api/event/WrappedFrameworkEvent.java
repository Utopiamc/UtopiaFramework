package de.utopiamc.framework.api.event;

import de.utopiamc.framework.api.event.qualifier.Event;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.MatchingInjectableCandidate;

public class WrappedFrameworkEvent extends FrameworkEvent {

    private final Object event;

    public WrappedFrameworkEvent(Object event) {
        this.event = event;
    }

    @Override
    public void addInjectableCandidates(CandidateQueue queue) {
        queue.addFirst(new MatchingInjectableCandidate(Event.class, event, this));
    }

    @Override
    public boolean isComparable(Class<?> cls) {
        return cls.isAssignableFrom(event.getClass()) || cls.isAssignableFrom(getClass());
    }
}
