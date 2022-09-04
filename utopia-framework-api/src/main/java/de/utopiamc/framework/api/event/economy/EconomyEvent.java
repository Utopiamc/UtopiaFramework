package de.utopiamc.framework.api.event.economy;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.event.economy.qualifier.Holder;
import de.utopiamc.framework.api.event.economy.qualifier.TargetEconomy;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.MatchingInjectableCandidate;
import de.utopiamc.framework.api.inject.OneToOneInjectableCandidate;
import de.utopiamc.framework.api.model.Economy;

public abstract class EconomyEvent extends FrameworkEvent {

    private final FrameworkPlayer holder;
    private final Economy economy;

    protected EconomyEvent(FrameworkPlayer holder, Economy economy) {
        this.holder = holder;
        this.economy = economy;
    }

    @Override
    public void addInjectableCandidates(CandidateQueue queue) {
        super.addInjectableCandidates(queue);
        queue.addFirst(new OneToOneInjectableCandidate(TargetEconomy.class, economy));
        queue.addFirst(new MatchingInjectableCandidate(Holder.class, holder, holder.getUuid(), holder.getName()));
    }

    public Economy getEconomy() {
        return economy;
    }

    public FrameworkPlayer getHolder() {
        return holder;
    }
}
