package de.utopiamc.framework.common.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PlayerDto {

    private String name;
    private UUID uuid;
    private Date firstJoined;

}
