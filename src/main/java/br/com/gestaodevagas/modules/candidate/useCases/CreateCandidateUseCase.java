package br.com.gestaodevagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gestaodevagas.exceptions.UserFoundException;
import br.com.gestaodevagas.modules.candidate.entities.CandidateEntity;
import br.com.gestaodevagas.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(),
                candidateEntity.getEmail()).ifPresent((user) -> {
                    throw new UserFoundException();
                });
            var passsword = this.passwordEncoder.encode(candidateEntity.getPassword());
            candidateEntity.setPassword(passsword);
        return this.candidateRepository.save(candidateEntity);
    }

}
