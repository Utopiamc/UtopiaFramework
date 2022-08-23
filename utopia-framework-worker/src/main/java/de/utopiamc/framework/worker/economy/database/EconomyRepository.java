package de.utopiamc.framework.worker.economy.database;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EconomyRepository extends Neo4jRepository<EconomyEntity, UUID> {

    List<EconomyEntity> findAllByName(String name);


}
