package de.utopiamc.framework.worker.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StatsDto {

    private final String game;
    private final PlayerDto player;
    private final Map<String, Integer> stats;

}
