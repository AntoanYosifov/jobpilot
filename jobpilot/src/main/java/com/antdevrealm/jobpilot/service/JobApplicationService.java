package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import org.springframework.data.domain.Pageable;


public interface JobApplicationService {
    JobApplicationResponseDTO apply(JobApplicationDTO dto, Long userId);

    JobApplicationResponseDTO getById(Long id);

    void deleteById(Long id);


    PaginatedResponse<JobApplicationResponseDTO> searchApplications(Long authorId,
                                                                    StatusEnum statusEnum,
                                                                    String companyName,
                                                                    String positionName,
                                                                    Pageable pageable);
}
