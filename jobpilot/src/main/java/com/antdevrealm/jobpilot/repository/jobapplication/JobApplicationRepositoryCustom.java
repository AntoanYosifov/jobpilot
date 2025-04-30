package com.antdevrealm.jobpilot.repository.jobapplication;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobApplicationRepositoryCustom {
    Page<JobApplicationEntity> searchApplications(StatusEnum statusEnum, String companyName, String positionName, Pageable pageable);
}
