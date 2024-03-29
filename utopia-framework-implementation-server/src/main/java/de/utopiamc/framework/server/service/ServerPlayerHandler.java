package de.utopiamc.framework.server.service;

import de.utopiamc.framework.api.model.RequestBodyType;
import de.utopiamc.framework.api.service.FrameworkPlayerService;
import de.utopiamc.framework.api.service.RequestService;
import de.utopiamc.framework.common.dto.PlayerDto;
import de.utopiamc.framework.common.dto.PlayerInfoDto;
import de.utopiamc.framework.common.messaging.StompHandler;
import de.utopiamc.framework.common.service.impl.CommonPlayerHandler;

import javax.inject.Inject;
import java.io.IOException;
import java.util.UUID;

public class ServerPlayerHandler extends CommonPlayerHandler {

    private final RequestService requestService;

    @Inject
    public ServerPlayerHandler(FrameworkPlayerService playerService, RequestService requestService, StompHandler stompHandler) {
        super(playerService, stompHandler);
        this.requestService = requestService;
    }

    @Override
    public void join(UUID uuid) {
        try {
            PlayerInfoDto info = requestService.post("/player/join")
                    .body("", RequestBodyType.APPLICATION_JSON)
                    .queryParam("player", uuid.toString())
                    .execute(PlayerInfoDto.class);

            PlayerDto player = info.getPlayer();
            loginFrameworkPlayerData(player.getUuid(), player.getName(), player.getFirstJoined());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void quit(UUID uuid) {
        super.quit(uuid);
    }
}
