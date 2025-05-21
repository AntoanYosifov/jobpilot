package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.model.dto.jobposting.JobPostingResponseDTO;

public interface JobPostingService {
    JobPostingResponseDTO getById(Long id);
    int refreshJobPostings(int page);
}
