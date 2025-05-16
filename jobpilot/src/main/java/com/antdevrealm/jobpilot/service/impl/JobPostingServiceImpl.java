package com.antdevrealm.jobpilot.service.impl;

import com.antdevrealm.jobpilot.integration.adzuna.AdzunaJobDTO;
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

    private static JobPostingEntity mapToEntity(AdzunaJobDTO dto) {
        return new JobPostingEntity(
                Long.valueOf(dto.id()),
                dto.title(),
                dto.company().display_name(),
                dto.location().display_name(),
                dto.description(),
                dto.redirect_url(),
                dto.created()
        );
    }
}
