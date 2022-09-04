package de.utopiamc.framework.common.messaging.packets;

import com.google.gson.Gson;
import de.utopiamc.framework.api.packets.BasePacket;
import de.utopiamc.framework.api.packets.PacketTypeRegistry;
import de.utopiamc.framework.common.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class PacketHandler implements PacketTypeRegistry {

    private final ApplicationContext context;
    private final Map<String, Class<? extends BasePacket>> packetTypes;

    @Inject
    public PacketHandler(ApplicationContext context) {
        this.context = context;
        this.packetTypes = new HashMap<>();
    }

    public void handlePlayer(String message) {
        Gson gson = new Gson();
        BasePacketDto basePacketDto = gson.fromJson(message, BasePacketDto.class);
        Class<? extends BasePacket> basePacketClass = packetTypes.get(basePacketDto.getPacketId());
        BasePacket basePacket = gson.fromJson(message, basePacketClass);
        basePacket.handle(context);
    }

    @Override
    public void registerPacketType(String packetTypeId, Class<? extends BasePacket> packetType) {
        if (packetType.isEnum() || Modifier.isAbstract(packetType.getModifiers()))
            return;
        packetTypes.put(packetTypeId, packetType);
    }
}
