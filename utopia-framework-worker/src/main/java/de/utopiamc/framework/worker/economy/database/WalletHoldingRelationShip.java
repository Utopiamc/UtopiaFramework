package de.utopiamc.framework.worker.economy.database;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class WalletHoldingRelationShip {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private EconomyEntity economy;

    private Double value;
}
