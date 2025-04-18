package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobRepo;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Override
    public JobApplicationResponseDTO apply(JobApplicationDTO dto) {
        JobApplicationEntity job = new JobApplicationEntity(
                dto.company(),
                dto.position(),
                StatusEnum.valueOf(dto.status().toUpperCase()),
                LocalDate.now()
        );

        JobApplicationEntity saved = jobRepo.save(job);

        return new JobApplicationResponseDTO(
                saved.getId(),
                saved.getCompany(),
                saved.getPosition(),
                saved.getStatus().name(),
                saved.getAppliedOn().toString(),
                saved.getFeedback()
        );

    }
}
