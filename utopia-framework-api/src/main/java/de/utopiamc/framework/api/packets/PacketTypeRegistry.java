package de.utopiamc.framework.api.packets;

public interface PacketTypeRegistry {

    void registerPacketType(String packetTypeId, Class<? extends BasePacket> packetType);

}
