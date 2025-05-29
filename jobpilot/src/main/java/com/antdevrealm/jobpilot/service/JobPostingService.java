package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.model.dto.jobposting.JobPostingResponseDTO;
import org.springframework.data.domain.Pageable;

public interface JobPostingService {
    JobPostingResponseDTO getById(Long id);
    int refreshJobPostings();

    PaginatedResponse<JobPostingResponseDTO> searchJobs(String positionName, String companyName, String locationName, Pageable pageable);
}
