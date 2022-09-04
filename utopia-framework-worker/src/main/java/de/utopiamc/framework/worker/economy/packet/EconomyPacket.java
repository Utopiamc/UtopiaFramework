package de.utopiamc.framework.worker.economy.packet;

import de.utopiamc.framework.worker.economy.dto.EconomyDto;
import de.utopiamc.framework.worker.packet.BasePacket;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
public abstract class EconomyPacket extends BasePacket {

    private final EconomyDto economy;
    private final UUID holder;

}
