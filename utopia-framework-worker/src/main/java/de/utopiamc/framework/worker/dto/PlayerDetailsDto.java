package de.utopiamc.framework.worker.dto;

import lombok.Data;

@Data
public class PlayerDetailsDto {

    private final PlayerDto player;

    private final BanDto ban;
}
