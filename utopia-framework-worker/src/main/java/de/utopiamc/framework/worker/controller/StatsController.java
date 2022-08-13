package de.utopiamc.framework.worker.controller;

import de.utopiamc.framework.worker.dto.StatsDto;
import de.utopiamc.framework.worker.dto.UpdateStatsBodyDto;
import de.utopiamc.framework.worker.service.StatsService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/stats")
@RestController
@Data
public class StatsController {

    private final StatsService statsService;

    @GetMapping()
    public ResponseEntity<StatsDto> getStats(@RequestParam UUID player,
                                             @RequestParam String game) {
        return statsService.getWrappedStats(player, game);
    }

    @PutMapping()
    public ResponseEntity<StatsDto> updateStats(@RequestParam UUID player,
                                                @RequestParam String game,
                                                @RequestBody UpdateStatsBodyDto body) {
        return statsService.updateWrappedStats(player, game, body);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteStats(@RequestParam UUID player,
                                         @RequestParam String game) {
        return statsService.deleteWrappedStats(player, game);
    }

}
