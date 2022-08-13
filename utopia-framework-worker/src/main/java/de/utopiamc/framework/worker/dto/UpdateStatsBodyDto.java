package de.utopiamc.framework.worker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class UpdateStatsBodyDto {

    private Map<String, Integer> properties;

}
