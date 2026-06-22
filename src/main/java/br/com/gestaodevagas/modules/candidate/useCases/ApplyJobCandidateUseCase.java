package br.com.gestaodevagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestaodevagas.modules.candidate.entities.ApplyJobEntity;
import br.com.gestaodevagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.gestaodevagas.modules.candidate.repositories.CandidateRepository;
import br.com.gestaodevagas.modules.company.repositories.JobRepository;

import br.com.gestaodevagas.exceptions.JobNotFoundException;
import br.com.gestaodevagas.exceptions.UserNotFoundException;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {
        this.candidateRepository.findById(candidateId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        var applyJobEntity = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .build();
        var applyJob = this.applyJobRepository.save(applyJobEntity);

        return applyJob;
    }

}
