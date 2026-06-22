package br.com.gestaodevagas.modules.candidate.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthCandidateRequestDTO(
        @NotBlank(message = "O campo [username] não pode estar vazio")
        String username,
        @NotBlank(message = "O campo [password] não pode estar vazio")
        String password
) {
    
}
