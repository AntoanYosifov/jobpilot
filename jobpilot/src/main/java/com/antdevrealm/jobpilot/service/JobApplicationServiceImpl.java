package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobRepo;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Override
    public List<JobApplicationResponseDTO> getAll() {
        List<JobApplicationEntity> allJobEntities = jobRepo.findAll();

        return allJobEntities.stream().map(this::mapToResponseDTO).toList();
    }

    @Override
    public JobApplicationResponseDTO getById(Long id) {
        return mapToResponseDTO(jobRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication with ID: " + id + " not found")));
    }

    @Override
    public void deleteById(Long id) {
       if(!jobRepo.existsById(id)) {
           throw new ResourceNotFoundException("JobApplication with ID: " + id + " not found");
       }

       jobRepo.deleteById(id);
    }

    @Override
    public JobApplicationResponseDTO apply(JobApplicationDTO dto) {
        JobApplicationEntity saved = jobRepo.save(mapToEntity(dto));

        return mapToResponseDTO(saved);
    }

    private JobApplicationResponseDTO mapToResponseDTO(JobApplicationEntity entity) {
        return new JobApplicationResponseDTO(
                entity.getId(),
                entity.getCompany(),
                entity.getPosition(),
                entity.getStatus().toString(),
                entity.getAppliedOn().toString(),
                entity.getFeedback()
        );
    }

    private JobApplicationEntity mapToEntity(JobApplicationDTO dto) {
        return new JobApplicationEntity(
                dto.company(),
                dto.position(),
                StatusEnum.valueOf(dto.status().toUpperCase()),
                LocalDate.now()
        );
    }

}
