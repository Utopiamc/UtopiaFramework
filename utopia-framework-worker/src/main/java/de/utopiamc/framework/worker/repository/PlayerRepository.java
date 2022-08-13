package de.utopiamc.framework.worker.repository;

import de.utopiamc.framework.worker.entity.Player;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends Neo4jRepository<Player, UUID> {



}
