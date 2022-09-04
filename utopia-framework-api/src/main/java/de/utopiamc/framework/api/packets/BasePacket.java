package de.utopiamc.framework.api.packets;

import de.utopiamc.framework.api.context.Context;

public abstract class BasePacket {

    public abstract void handle(Context context);

}
