package de.utopiamc.framework.api.event.economy;

import de.utopiamc.framework.api.entity.FrameworkPlayer;
import de.utopiamc.framework.api.event.economy.qualifier.Cause;
import de.utopiamc.framework.api.event.economy.qualifier.PreviousValue;
import de.utopiamc.framework.api.event.economy.qualifier.Value;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.OneToOneInjectableCandidate;
import de.utopiamc.framework.api.model.Economy;

public class EconomyHoldingsUpdateEvent extends EconomyEvent {

    private final EconomyHoldingUpdateCause cause;
    private final Double previousValue;
    private final Double newValue;

    public EconomyHoldingsUpdateEvent(FrameworkPlayer holder, Economy economy, EconomyHoldingUpdateCause cause, Double previousValue, Double newValue) {
        super(holder, economy);
        this.cause = cause;
        this.previousValue = previousValue;
        this.newValue = newValue;
    }

    @Override
    public void addInjectableCandidates(CandidateQueue queue) {
        super.addInjectableCandidates(queue);
        queue.addFirst(new OneToOneInjectableCandidate(PreviousValue.class, previousValue));
        queue.addFirst(new OneToOneInjectableCandidate(Value.class, newValue));
        queue.addFirst(new OneToOneInjectableCandidate(Cause.class, cause));
    }
}
