package br.com.gestaodevagas.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {
    @NotBlank(message = "O campo [username] é obrigatório")
    private String username;

    @NotBlank(message = "O campo [password] é obrigatório")
    private String password;
    
}
