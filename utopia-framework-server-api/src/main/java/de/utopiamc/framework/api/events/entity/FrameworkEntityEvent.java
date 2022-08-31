package de.utopiamc.framework.api.events.entity;

import de.utopiamc.framework.api.events.BukkitEvent;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.OneToOneInjectableCandidate;
import org.bukkit.entity.Entity;

public class FrameworkEntityEvent extends BukkitEvent {

    protected final Entity entity;

    public FrameworkEntityEvent(org.bukkit.event.entity.EntityEvent event) {
        super(event);

        this.entity = event.getEntity();
    }

    @Override
    public void addInjectableCandidates(CandidateQueue queue) {
        super.addInjectableCandidates(queue);
        queue.addFirst(new OneToOneInjectableCandidate(de.utopiamc.framework.api.events.entity.qualifier.Entity.class, entity));
    }
}
