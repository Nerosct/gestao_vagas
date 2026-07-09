package br.com.gestaodevagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.gestaodevagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.gestaodevagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.gestaodevagas.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO AuthCandidateRequestDTO)
            throws AuthenticationException {
        var candidate = candidateRepository.findByUsername(AuthCandidateRequestDTO.username()).orElseThrow(() -> {
            return new UsernameNotFoundException("Usuário/Senha Incorretos");
        });
        var passwordMatch = this.passwordEncoder.matches(AuthCandidateRequestDTO.password(), candidate.getPassword());
        if (!passwordMatch) {
            throw new AuthenticationException("Usuário/Senha Incorretos");
        }

        var roles = Arrays.asList("CANDIDATE");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create().withIssuer("Javagas")
                .withExpiresAt(expiresAt)
                .withSubject(candidate.getId().toString())
                .withClaim("roles", roles)
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresAt.toEpochMilli())
                .roles(roles)
                .build();
        return authCandidateResponse;
    }

}
