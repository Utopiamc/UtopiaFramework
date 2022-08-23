package de.utopiamc.framework.worker.economy.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class TransactionResponseDto {

    boolean success;

    Double newValue;




}
