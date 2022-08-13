package de.utopiamc.framework.worker.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PlayerDto {

    private final UUID uuid;
    private final String name;
    private final Date firstJoined;

}
