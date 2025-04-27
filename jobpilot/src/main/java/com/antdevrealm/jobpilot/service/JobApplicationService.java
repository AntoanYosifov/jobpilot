package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;

import java.util.List;


public interface JobApplicationService {
    JobApplicationResponseDTO apply(JobApplicationDTO dto);

    PaginatedResponse<JobApplicationResponseDTO> getAll(int page, int size);

    JobApplicationResponseDTO getById(Long id);

    void deleteById(Long id);

    JobApplicationResponseDTO updateById(Long id, JobApplicationDTO dto);

    PaginatedResponse<JobApplicationResponseDTO> getByStatus(StatusEnum statusEnum, int page, int size);

    List<JobApplicationResponseDTO> getByCompany(String companyName);
}
