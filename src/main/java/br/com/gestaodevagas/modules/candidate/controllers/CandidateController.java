package br.com.gestaodevagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import br.com.gestaodevagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gestaodevagas.modules.candidate.entities.ApplyJobEntity;
import br.com.gestaodevagas.modules.candidate.entities.CandidateEntity;
import br.com.gestaodevagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.gestaodevagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.gestaodevagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.gestaodevagas.modules.company.entities.JobEntity;
import br.com.gestaodevagas.modules.company.useCases.ListAllJobsByFilterUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;


    @PostMapping("/")
    @Operation(summary = "Cadastro de Candidato", description = "Função responsável por realizar o cadastro do candidato.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CandidateEntity.class))),
            @ApiResponse(responseCode = "400", description = "Usuário já existente")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Perfil do candidato", description = "Busca as informações do candidato logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "User not Found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getProfileCandidate(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas", description = "Busca as vagas disponíveis baseadas em algum filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))),
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String jobDescriptionFilter) {
        var result = this.listAllJobsByFilterUseCase.execute(jobDescriptionFilter);
        return result;
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Aplicar para vaga", description = "Candidato aplica para uma vaga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ApplyJobEntity.class))),
            @ApiResponse(responseCode = "400", description = "Candidato ou vaga não encontrados")
    })
    public ResponseEntity<Object> applyJobCandidate(HttpServletRequest request, @RequestBody UUID jobId) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), jobId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
