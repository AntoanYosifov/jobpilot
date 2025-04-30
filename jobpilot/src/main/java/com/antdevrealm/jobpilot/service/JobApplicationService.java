package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;


public interface JobApplicationService {
    JobApplicationResponseDTO apply(JobApplicationDTO dto);

    JobApplicationResponseDTO getById(Long id);

    void deleteById(Long id);

    JobApplicationResponseDTO updateById(Long id, JobApplicationDTO dto);

    PaginatedResponse<JobApplicationResponseDTO> searchApplications(StatusEnum statusEnum,
                                                                    String companyName,
                                                                    String positionName,
                                                                    String sortDir,
                                                                    int page,
                                                                    int size);
}
