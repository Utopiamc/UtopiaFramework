package de.utopiamc.framework.worker.economy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class EconomyTransactionDto {

    @NotNull private UUID holder;
    private UUID to;

    @NotNull private UUID economy;

    @NotNull private String action;

    private Double value;
    private Double amount;

}
