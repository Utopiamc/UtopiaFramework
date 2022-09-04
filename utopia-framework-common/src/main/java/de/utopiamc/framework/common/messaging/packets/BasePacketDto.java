package de.utopiamc.framework.common.messaging.packets;

import lombok.Data;

import java.util.UUID;

@Data
public class BasePacketDto {

    private String packetId;
    private UUID toPlayer;

}
