package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;

import java.util.List;


public interface JobApplicationService {
    JobApplicationResponseDTO apply(JobApplicationDTO dto);

    List<JobApplicationResponseDTO> getAll();

    JobApplicationResponseDTO getById(Long id);
}
