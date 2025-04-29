package com.antdevrealm.jobpilot.service;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.repository.JobApplicationRepository;
import com.antdevrealm.jobpilot.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobRepo;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Override
    public PaginatedResponse<JobApplicationResponseDTO> searchApplications(StatusEnum statusEnum,
                                                                           String companyName,
                                                                           String positionName,
                                                                           String sortDir,
                                                                           int page,
                                                                           int size) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "appliedOn");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<JobApplicationEntity> applicationEntityPage = jobRepo.searchApplications(
                statusEnum, companyName, positionName, pageable);

        Page<JobApplicationResponseDTO> dtoPage = applicationEntityPage.map(this::mapToResponseDTO);

        return PaginationUtil.wrap(dtoPage);
    }


    @Override
    public JobApplicationResponseDTO getById(Long id) {
        return mapToResponseDTO(jobRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication with ID: " + id + " not found")));
    }

    @Override
    public JobApplicationResponseDTO apply(JobApplicationDTO dto) {
        JobApplicationEntity saved = jobRepo.save(mapToEntity(dto));

        return mapToResponseDTO(saved);
    }

    @Override
    public JobApplicationResponseDTO updateById(Long id, JobApplicationDTO dto) {

        JobApplicationEntity forUpdate = jobRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication with ID: " + id + " not found"));

        if (dto.company() != null) {
            forUpdate.setCompany(dto.company());
        }
        if (dto.position() != null) {
            forUpdate.setPosition(dto.position());
        }
        if (dto.status() != null) {
            forUpdate.setStatus(StatusEnum.valueOf(dto.status().toUpperCase()));
        }

        JobApplicationEntity updated = jobRepo.save(forUpdate);
        return mapToResponseDTO(updated);
    }


    @Override
    public void deleteById(Long id) {
        if (!jobRepo.existsById(id)) {
            throw new ResourceNotFoundException("JobApplication with ID: " + id + " not found");
        }

        jobRepo.deleteById(id);
    }

    private JobApplicationResponseDTO mapToResponseDTO(JobApplicationEntity entity) {
        return new JobApplicationResponseDTO(
                entity.getId(),
                entity.getCompany(),
                entity.getPosition(),
                entity.getStatus().toString(),
                entity.getAppliedOn().toString(),
                entity.getFeedback()
        );
    }

    private JobApplicationEntity mapToEntity(JobApplicationDTO dto) {
        return new JobApplicationEntity(
                dto.company(),
                dto.position(),
                StatusEnum.valueOf(dto.status().toUpperCase()),
                LocalDate.now()
        );
    }
}
