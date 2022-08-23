package de.utopiamc.framework.worker.economy.database;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.GeneratedValue.UUIDGenerator;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Wallet")
@Data
public class WalletEntity {

    @Id
    @GeneratedValue(UUIDGenerator.class)
    private UUID id;

    @Relationship(type = "HOLD_ECONOMY", direction = OUTGOING)
    private List<WalletHoldingRelationShip> holdings;

}
