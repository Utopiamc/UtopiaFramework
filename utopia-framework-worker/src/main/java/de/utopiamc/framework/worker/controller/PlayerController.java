package de.utopiamc.framework.worker.controller;

import de.utopiamc.framework.worker.dto.PlayerDetailsDto;
import de.utopiamc.framework.worker.dto.PlayerDto;
import de.utopiamc.framework.worker.service.PlayerService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Data
@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/join")
    public ResponseEntity<PlayerDetailsDto> join(@RequestParam UUID player) {
        return playerService.join(player);
    }

    @PostMapping("/quit")
    public ResponseEntity<PlayerDto> quit(@RequestParam UUID player) {
        return null;
    }

    @GetMapping
    public ResponseEntity<PlayerDto> get(@RequestParam UUID uuid) {
        return playerService.getWrappedPlayer(uuid);
    }

}
