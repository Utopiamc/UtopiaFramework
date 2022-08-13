package de.utopiamc.framework.worker.entity.relations;

import de.utopiamc.framework.worker.entity.Player;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class PlayerStatsRelationship {

    @Id
    @GeneratedValue
    private Long relId;

    @TargetNode
    private final Player player;

    private final String game;

}
