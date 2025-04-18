package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;


public interface JobApplicationService {
    JobApplicationResponseDTO apply(JobApplicationDTO dto);
}
