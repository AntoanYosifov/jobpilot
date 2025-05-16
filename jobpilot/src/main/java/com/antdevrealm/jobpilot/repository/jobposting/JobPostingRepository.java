package com.antdevrealm.jobpilot.repository.jobposting;

import com.antdevrealm.jobpilot.model.entity.JobPostingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPostingEntity, Long> {

}
