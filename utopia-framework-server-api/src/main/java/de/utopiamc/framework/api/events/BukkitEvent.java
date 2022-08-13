package de.utopiamc.framework.api.events;

import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.MatchingInjectableCandidate;
import org.bukkit.event.Event;

public class BukkitEvent extends FrameworkEvent {

    private final Event event;

    public BukkitEvent(Event event) {
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
