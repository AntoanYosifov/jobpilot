package com.antdevrealm.jobpilot.repository;

import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Integer> {
}
