package br.com.gestaodevagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {

    @Schema(example = "Analista de Sistemas Pleno", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "Vale-refeição, Vale-transporte", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Pleno", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}