package de.utopiamc.framework.worker.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;
import java.util.UUID;

@Node("Player")
@Data
public class Player {

    @Id
    private UUID uuid;

    private String name;
    private LocalDateTime firstJoined;

}
