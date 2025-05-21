package com.antdevrealm.jobpilot.service.impl;

import com.antdevrealm.jobpilot.config.AdzunaPropertiesConfig;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.integration.adzuna.AdzunaJobClient;
import com.antdevrealm.jobpilot.integration.adzuna.dto.AdzunaJobDTO;
import com.antdevrealm.jobpilot.model.dto.jobposting.JobPostingResponseDTO;
import com.antdevrealm.jobpilot.model.entity.JobPostingEntity;
import com.antdevrealm.jobpilot.repository.jobposting.JobPostingRepository;
import com.antdevrealm.jobpilot.service.JobPostingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository repo;
    private final AdzunaJobClient client;

    public JobPostingServiceImpl(JobPostingRepository repo, AdzunaJobClient client) {
        this.repo = repo;
        this.client = client;

    }

    @Override
    public JobPostingResponseDTO getById(Long id) {
        return mapToResponseDTO(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job Posting with id: " + id + " not found!")));
    }

    @Override
    public int refreshJobPostings(int page) {
        List<AdzunaJobDTO> dtos = client.fetchJobs(page);
        List<JobPostingEntity> entities = dtos.stream().map(JobPostingServiceImpl::mapToEntity)
                .toList();

        List<JobPostingEntity> saved = repo.saveAll(entities);

        return saved.size();
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
