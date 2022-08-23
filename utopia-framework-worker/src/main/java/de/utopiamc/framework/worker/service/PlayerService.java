package de.utopiamc.framework.worker.service;

import de.utopiamc.framework.worker.dto.PlayerDetailsDto;
import de.utopiamc.framework.worker.entity.Player;
import de.utopiamc.framework.worker.exception.PlayerNotFoundException;
import de.utopiamc.framework.worker.repository.PlayerRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Data
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final DtoService dtoService;

    public Player getPlayer(UUID player) {
        return playerRepository.findById(player).orElseThrow(PlayerNotFoundException::new);
    }

    public ResponseEntity<PlayerDetailsDto> join(UUID uuid) {
        Player player = getPlayer(uuid);

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoService.toDto(player, null));
    }
}
