package de.utopiamc.framework.common.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.entity.PlayerServiceHolder;
import de.utopiamc.framework.api.packets.PacketTypeRegistry;
import de.utopiamc.framework.api.service.ColorService;
import de.utopiamc.framework.api.service.EconomyService;
import de.utopiamc.framework.api.service.RequestService;
import de.utopiamc.framework.api.service.TempEventService;
import de.utopiamc.framework.common.messaging.StompHandler;
import de.utopiamc.framework.common.messaging.packets.PacketHandler;
import de.utopiamc.framework.common.service.impl.CommonColorService;
import de.utopiamc.framework.common.service.impl.CommonEconomyService;
import de.utopiamc.framework.common.service.impl.CommonRequestService;
import de.utopiamc.framework.common.service.impl.CommonTempEventService;

public class CommonApisModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ColorService.class).to(CommonColorService.class);
        bind(TempEventService.class).to(CommonTempEventService.class);
        bind(RequestService.class).to(CommonRequestService.class);
        bind(EconomyService.class).to(CommonEconomyService.class);

        // Messaging
        bind(StompHandler.class).asEagerSingleton();
        bind(PacketHandler.class).asEagerSingleton();
        bind(PacketTypeRegistry.class).to(PacketHandler.class);

        requestStaticInjection(PlayerServiceHolder.class);
    }
}
