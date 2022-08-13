package de.utopiamc.framework.worker.controller;

import de.utopiamc.framework.worker.dto.PlayerDetailsDto;
import de.utopiamc.framework.worker.dto.PlayerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @PostMapping("/join")
    public ResponseEntity<PlayerDetailsDto> join(@RequestParam UUID player) {
        return null;
    }

    @PostMapping("/quit")
    public ResponseEntity<PlayerDto> quit(@RequestParam UUID player) {
        return null;
    }

}
