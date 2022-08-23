package de.utopiamc.framework.worker.economy.database;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.GeneratedValue.UUIDGenerator;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Data
@Node("Economy")
public class EconomyEntity {

    @Id
    @GeneratedValue(UUIDGenerator.class)
    private UUID id;

    private String name;

    private String symbol;

    private Short value;

}
