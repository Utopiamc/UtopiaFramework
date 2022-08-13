package de.utopiamc.framework.worker.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CurrencyDto {

    private final UUID id;
    private final String display;
    private final String name;
    private final double value;

}
