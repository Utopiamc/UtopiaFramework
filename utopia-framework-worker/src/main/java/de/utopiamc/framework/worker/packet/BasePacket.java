package de.utopiamc.framework.worker.packet;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class BasePacket {

    private final String packetId;

}
