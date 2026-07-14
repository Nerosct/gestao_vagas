package br.com.gestaodevagas.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.gestaodevagas.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    List<JobEntity> findByDescriptionContainingIgnoreCase(String jobDescriptionFilter);

    List<JobEntity> findByCompanyId(UUID companyId);

}
