package de.utopiamc.framework.common.packets;

import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.event.economy.EconomyHoldingUpdateCause;
import de.utopiamc.framework.api.event.economy.EconomyHoldingsUpdateEvent;

public class EconomyHoldingsUpdatePacket extends EconomyPacket {

    private Double previousValue;
    private Double newValue;
    private EconomyHoldingUpdateCause cause;

    @Override
    public void handle(Context context) {
        context.dispatchEvent(new EconomyHoldingsUpdateEvent(context.getFrameworkPlayer(holder), economy, cause, previousValue, newValue));
    }

}
