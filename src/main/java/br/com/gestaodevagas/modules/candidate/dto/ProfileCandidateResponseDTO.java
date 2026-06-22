package br.com.gestaodevagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    @Schema(example = "0190e079-886b-7743-a5da-3b7eb98468ba")
    private UUID id;
    
    @Schema(example = "Nero")
    private String name;
    
    @Schema(example = "[EMAIL_ADDRESS]")
    private String email;
    
    @Schema(example = "neron")
    private String username;
    
    @Schema(example = "Desenvolvedor Java")
    private String description;
}