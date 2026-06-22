package br.com.gestaodevagas.modules.candidate.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(example = "0190e079-886b-7743-a5da-3b7eb98468ba", requiredMode = RequiredMode.REQUIRED, description = "ID do Candidato")
    private UUID id;

    @Schema(example = "Neron", requiredMode = RequiredMode.REQUIRED, description = "Nome do Candidato")

    private String name;

    @Pattern(regexp = "\\S+", message = "O campo [username] e o campo [email] não pode conter espaços")
    @Schema(example = "neron", requiredMode = RequiredMode.REQUIRED, description = "Username do Candidato")
    @NotBlank
    private String username;

    @Email(message = "O campo [email] deve ser válido")
    @Schema(example = "[EMAIL_ADDRESS]", requiredMode = RequiredMode.REQUIRED, description = "Email do Candidato")
    @NotBlank
    private String email;

    @Schema(example = "Admin@1234", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do Candidato")
    private String password;

    @Schema(example = "Desenvolvedor Java", requiredMode = RequiredMode.REQUIRED, description = "Descrição do Candidato")
    private String description;

    @Schema(example = "Curriculum", requiredMode = RequiredMode.REQUIRED, description = "Curriculum do Candidato")
    private String curriculum;

    @CreationTimestamp
    private LocalDate createdAt;

}
