package de.utopiamc.framework.common.packets;

import de.utopiamc.framework.api.packets.BasePacket;
import de.utopiamc.framework.common.dto.CommonEconomy;

import java.util.UUID;

public abstract class EconomyPacket extends BasePacket {

    protected CommonEconomy economy;
    protected UUID holder;

}
