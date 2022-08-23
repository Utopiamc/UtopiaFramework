package de.utopiamc.framework.common.dto;

import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString
public class PlayerDto {

    private String name;
    private UUID uuid;
    private Date firstJoined;

}
