package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.integration.adzuna.AdzunaJobDTO;
import com.antdevrealm.jobpilot.model.dto.jobposting.JobPostingResponseDTO;
import com.antdevrealm.jobpilot.model.entity.JobPostingEntity;

public interface JobPostingService {
    JobPostingEntity save(AdzunaJobDTO dto);
    JobPostingResponseDTO getById(Long id);
}
