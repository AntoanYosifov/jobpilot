package com.antdevrealm.jobpilot.repository.jobposting.specification;

import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.model.entity.JobPostingEntity;
import org.springframework.data.jpa.domain.Specification;

public class JobPostingSpecs {
    public static Specification<JobPostingEntity> positionLike(String positionName) {
        return (root, query, cb) -> (positionName == null || positionName.isEmpty()) ? cb.conjunction()
                : cb.like(cb.lower(root.get("title")), "%" + positionName.toLowerCase() + "%");
    }

    public static Specification<JobPostingEntity> companyLike(String companyName) {
        return (root, query, cb) -> (companyName == null || companyName.isEmpty()) ? cb.conjunction()
                : cb.like(cb.lower(root.get("companyName")), "%" + companyName.toLowerCase() + "%");
    }

    public static Specification<JobPostingEntity> locationLike(String locationName) {
        return (root, query, cb) -> (locationName == null || locationName.isEmpty()) ? cb.conjunction()
                : cb.like(cb.lower(root.get("locationName")), "%" + locationName.toLowerCase() + "%");
    }
}
