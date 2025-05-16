package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.integration.adzuna.AdzunaJobDTO;
import com.antdevrealm.jobpilot.model.entity.JobPostingEntity;

public interface JobPostingService {
    JobPostingEntity save(AdzunaJobDTO dto);
}
