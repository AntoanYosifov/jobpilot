package com.antdevrealm.jobpilot.service.impl;

import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.integration.adzuna.AdzunaJobDTO;
import com.antdevrealm.jobpilot.model.dto.jobposting.JobPostingResponseDTO;
import com.antdevrealm.jobpilot.model.entity.JobPostingEntity;
import com.antdevrealm.jobpilot.repository.jobposting.JobPostingRepository;
import com.antdevrealm.jobpilot.service.JobPostingService;
import org.springframework.stereotype.Service;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository jobPostingRepo;

    public JobPostingServiceImpl(JobPostingRepository jobPostingRepo) {
        this.jobPostingRepo = jobPostingRepo;
    }

    @Override
    public JobPostingEntity save(AdzunaJobDTO dto) {
        return jobPostingRepo.save(mapToEntity(dto));
    }

    @Override
    public JobPostingResponseDTO getById(Long id) {
        return mapToResponseDTO(jobPostingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job Posting with id: " + id + " not found!")));
    }

    private static JobPostingResponseDTO mapToResponseDTO(JobPostingEntity entity) {
        return new JobPostingResponseDTO(
                entity.getTitle(),
                entity.getCompanyName(),
                entity.getLocationName(),
                entity.getDescription(),
                entity.getRedirect_url(),
                entity.getExternalCreatedAt(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }

    private static JobPostingEntity mapToEntity(AdzunaJobDTO dto) {
        JobPostingEntity jobPostingEntity = new JobPostingEntity(
                Long.valueOf(dto.id()),
                dto.title(),
                dto.company().display_name(),
                dto.location().display_name(),
                dto.description(),
                dto.redirect_url(),
                dto.created()
        );

        jobPostingEntity.setLatitude(dto.latitude());
        jobPostingEntity.setLongitude(dto.longitude());

        return jobPostingEntity;
    }
}
