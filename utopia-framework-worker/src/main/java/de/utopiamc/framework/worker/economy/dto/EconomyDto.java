package de.utopiamc.framework.worker.economy.dto;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class EconomyDto {

    UUID id;
    String name;
    String symbol;
    Short value;

}
