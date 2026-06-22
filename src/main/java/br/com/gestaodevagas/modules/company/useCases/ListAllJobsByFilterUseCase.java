package br.com.gestaodevagas.modules.company.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gestaodevagas.modules.company.entities.JobEntity;
import br.com.gestaodevagas.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String jobDescriptionFilter){
        return this.jobRepository.findByDescriptionContainingIgnoreCase(jobDescriptionFilter);
    }
}
