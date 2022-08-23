package de.utopiamc.framework.worker.economy.database;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends Neo4jRepository<WalletEntity, UUID> {

    @Query("MATCH ({uuid: $holder})-[]->(w:Wallet), (w)-[r:HOLD_ECONOMY]->(e:Economy) RETURN w, collect(r) as holding, collect(e)")
    Optional<WalletEntity> getAllHoldings(UUID holder);

    @Query("MATCH ({uuid: $holder})-[]->(w:Wallet), (w)-[r:HOLD_ECONOMY]->(e:Economy {id: $economy}) RETURN w, r, e")
    Optional<WalletEntity> getHoldingByEconomy(UUID holder, UUID economy);

}
