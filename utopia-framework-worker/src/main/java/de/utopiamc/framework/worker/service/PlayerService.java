package de.utopiamc.framework.worker.service;

import de.utopiamc.framework.worker.entity.Player;
import de.utopiamc.framework.worker.exception.PlayerNotFoundException;
import de.utopiamc.framework.worker.repository.PlayerRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Data
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player getPlayer(UUID player) {
        return playerRepository.findById(player).orElseThrow(PlayerNotFoundException::new);
    }

}
