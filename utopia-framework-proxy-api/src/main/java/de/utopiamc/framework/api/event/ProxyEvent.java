package de.utopiamc.framework.api.event;

import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.MatchingInjectableCandidate;
import net.md_5.bungee.api.plugin.Event;

public class ProxyEvent extends FrameworkEvent {

    private final Event event;

    public ProxyEvent(Event event) {
        this.event = event;
    }

    @Override
    public void addInjectableCandidates(CandidateQueue queue) {
        queue.addFirst(new MatchingInjectableCandidate(de.utopiamc.framework.api.event.qualifier.Event.class, event, this));
    }

    @Override
    public boolean isComparable(Class<?> cls) {
        return cls.isAssignableFrom(getClass()) || cls.isAssignableFrom(event.getClass());
    }

    public Event getEvent() {
        return event;
    }

}
