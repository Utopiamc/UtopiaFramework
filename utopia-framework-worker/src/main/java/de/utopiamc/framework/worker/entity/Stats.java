package de.utopiamc.framework.worker.entity;

import de.utopiamc.framework.worker.entity.relations.PlayerStatsRelationship;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Node("Stats")
@Data
public class Stats {

    @Id
    @GeneratedValue(generatorClass = GeneratedValue.UUIDGenerator.class)
    private UUID id;

    @Relationship("HAS_STAT")
    private PlayerStatsRelationship relation;

    @CompositeProperty
    private Map<String, Integer> properties = new HashMap<>();

}
