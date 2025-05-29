package com.antdevrealm.jobpilot.service.impl;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.repository.jobapplication.JobApplicationRepository;
import com.antdevrealm.jobpilot.repository.jobapplication.specification.JobApplicationSpecs;
import com.antdevrealm.jobpilot.repository.user.UserRepository;
import com.antdevrealm.jobpilot.service.JobApplicationService;
import com.antdevrealm.jobpilot.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobAppRepo;
    private final UserRepository userRepo;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobAppRepo, UserRepository userRepo) {
        this.jobAppRepo = jobAppRepo;
        this.userRepo = userRepo;
    }

    @Override
    public PaginatedResponse<JobApplicationResponseDTO> searchApplications(Long authorId,
                                                                           StatusEnum statusEnum,
                                                                           String companyName,
                                                                           String positionName,
                                                                           Pageable pageable) {

         userRepo.findByEmail("test@mail.com").orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Specification<JobApplicationEntity> spec = Specification.where(JobApplicationSpecs.hasAuthorId(authorId))
                .and(JobApplicationSpecs.hasStatus(statusEnum))
                .and(JobApplicationSpecs.companyLike(companyName))
                .and(JobApplicationSpecs.positionLike(positionName));

        Page<JobApplicationEntity> entityPage = jobAppRepo.findAll(spec, pageable);

        Page<JobApplicationResponseDTO> dtoPge = entityPage.map(this::mapToResponseDTO);

        return PaginationUtil.wrap(dtoPge);
    }


    @Override
    public JobApplicationResponseDTO getById(Long id) {
        return mapToResponseDTO(jobAppRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication with ID: " + id + " not found")));
    }

    @Override
    public JobApplicationResponseDTO apply(JobApplicationDTO dto) {
        JobApplicationEntity saved = jobAppRepo.save(mapToEntity(dto));
        return mapToResponseDTO(saved);
    }

    @Override
    public JobApplicationResponseDTO updateById(Long id, JobApplicationDTO dto) {

        JobApplicationEntity forUpdate = jobAppRepo.findById(id)
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

        JobApplicationEntity updated = jobAppRepo.save(forUpdate);
        return mapToResponseDTO(updated);
    }


    @Override
    public void deleteById(Long id) {
        if (!jobAppRepo.existsById(id)) {
            throw new ResourceNotFoundException("JobApplication with ID: " + id + " not found");
        }

        jobAppRepo.deleteById(id);
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
