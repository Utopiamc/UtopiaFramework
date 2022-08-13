package de.utopiamc.framework.worker.dto;

import lombok.Data;

import javax.validation.Valid;
import java.util.UUID;

@Data
@Valid
public class TransactionDto {

    private UUID from;
    private UUID to;

    private double transferAmount;

}
