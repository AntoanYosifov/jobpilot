package com.antdevrealm.jobpilot.service.impl;

import com.antdevrealm.jobpilot.config.AdzunaPropertiesConfig;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.integration.adzuna.AdzunaJobClient;
import com.antdevrealm.jobpilot.integration.adzuna.dto.AdzunaJobDTO;
import com.antdevrealm.jobpilot.integration.adzuna.dto.AdzunaResponseDTO;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.model.dto.jobposting.JobPostingResponseDTO;
import com.antdevrealm.jobpilot.model.entity.JobPostingEntity;
import com.antdevrealm.jobpilot.repository.jobposting.JobPostingRepository;
import com.antdevrealm.jobpilot.repository.jobposting.specification.JobPostingSpecs;
import com.antdevrealm.jobpilot.service.JobPostingService;
import com.antdevrealm.jobpilot.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository jobPostRepo;
    private final AdzunaJobClient client;
    private final AdzunaPropertiesConfig props;

    public JobPostingServiceImpl(JobPostingRepository jobPostRepo, AdzunaJobClient client, AdzunaPropertiesConfig props) {
        this.jobPostRepo = jobPostRepo;
        this.client = client;
        this.props = props;
    }

    @Override
    public PaginatedResponse<JobPostingResponseDTO> searchJobs(String positionName,
                                                               String companyName,
                                                               String locationName,
                                                               Pageable pageable) {
        Specification<JobPostingEntity> spec = Specification.where(JobPostingSpecs.positionLike(positionName))
                .and(JobPostingSpecs.companyLike(companyName))
                .and(JobPostingSpecs.locationLike(locationName));

        Page<JobPostingEntity> entityPage = jobPostRepo.findAll(spec, pageable);

        Page<JobPostingResponseDTO> dtoPage = entityPage.map(JobPostingServiceImpl::mapToResponseDTO);

        return PaginationUtil.wrap(dtoPage);
    }


    @Override
    public JobPostingResponseDTO getById(Long id) {
        return mapToResponseDTO(jobPostRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job Posting with id: " + id + " not found!")));
    }

    @Override
    public int refreshJobPostings() {
        AdzunaResponseDTO first = client.fetchJobs(1);
        int totalPostings = Optional.ofNullable(first)
                .map(AdzunaResponseDTO::count).orElse(0);

        int pageSize = props.getDefaultResultsPerPage();
        int totalPages = (int)Math.ceil((double) totalPostings / pageSize);

        int randomPage = ThreadLocalRandom.current()
                .nextInt(1, totalPages + 1);

        List<AdzunaJobDTO> dtos = client.fetchJobs(randomPage).results();

        List<JobPostingEntity> entities = dtos.stream().map(JobPostingServiceImpl::mapToEntity).toList();

        List<JobPostingEntity> saved = jobPostRepo.saveAll(entities);

        return saved.size();
    }


    private static JobPostingResponseDTO mapToResponseDTO(JobPostingEntity entity) {
        return new JobPostingResponseDTO(
                entity.getId(),
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
