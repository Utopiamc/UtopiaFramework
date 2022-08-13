package de.utopiamc.framework.worker.service;

import de.utopiamc.framework.worker.dto.StatsDto;
import de.utopiamc.framework.worker.dto.UpdateStatsBodyDto;
import de.utopiamc.framework.worker.entity.relations.PlayerStatsRelationship;
import de.utopiamc.framework.worker.entity.Stats;
import de.utopiamc.framework.worker.exception.PlayerNotFoundException;
import de.utopiamc.framework.worker.repository.StatsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class StatsService {

    private final StatsRepository statsRepository;
    private final PlayerService playerService;
    private final DtoService dtoService;

    public ResponseEntity<StatsDto> getWrappedStats(UUID player, String game) {
        try {
            return ResponseEntity.ok(dtoService
                    .toDto(getStats(player, game)));
        }catch (PlayerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<StatsDto> updateWrappedStats(UUID player, String game, UpdateStatsBodyDto body) {
        return ResponseEntity.ok(dtoService.toDto(updateStats(player, game, body)));
    }

    public ResponseEntity<?> deleteWrappedStats(UUID player, String game) {
        deleteStats(player, game);
        return ResponseEntity.ok().build();
    }

    private Stats updateStats(UUID player, String game, UpdateStatsBodyDto body) {
        // Request Stats
        Stats stats = getStats(player, game);

        // Update Stats Entity
        body.getProperties().forEach((key, value) -> stats.getProperties().merge(key, value, Integer::sum));

        // Safe Stats Entity
        statsRepository.save(stats);

        return stats;
    }

    private void deleteStats(UUID player, String game) {
        Stats stats = getStats(player, game);

        statsRepository.delete(stats);
    }

    private Stats getStats(UUID player, String game) {
        return statsRepository
                .getStatsByRelation(game, player).orElseGet(() -> craftStats(player, game));
    }

    private Stats craftStats(UUID player, String game) {
        Stats stats = new Stats();

        PlayerStatsRelationship playerStatsRelationship = new PlayerStatsRelationship(playerService.getPlayer(player), game);
        stats.setRelation(playerStatsRelationship);
        statsRepository.save(stats);

        return stats;
    }
}
