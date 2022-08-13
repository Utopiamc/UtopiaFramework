package de.utopiamc.framework.worker.repository;

import de.utopiamc.framework.worker.entity.Stats;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatsRepository extends Neo4jRepository<Stats, UUID> {

    @Query("MATCH (s:Stats)-[r:HAS_STAT {game: $game}]-(p:Player {uuid: $player}) RETURN s, r, p")
    Optional<Stats> getStatsByRelation(String game, UUID player);

}
