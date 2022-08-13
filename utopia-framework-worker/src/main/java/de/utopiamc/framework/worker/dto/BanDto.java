package de.utopiamc.framework.worker.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BanDto {

    private final String reason;
    private final PlayerDto banner;
    private final Date endDate;

}
