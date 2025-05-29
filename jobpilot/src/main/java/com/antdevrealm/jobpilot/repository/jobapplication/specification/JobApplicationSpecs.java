package com.antdevrealm.jobpilot.repository.jobapplication.specification;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import org.springframework.data.jpa.domain.Specification;

public class JobApplicationSpecs {

    public static Specification<JobApplicationEntity> hasAuthorId(Long authorId) {
        return (root, query, cb) -> authorId == null ? cb.conjunction()
                : cb.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<JobApplicationEntity> hasStatus(StatusEnum status) {
        return (root, query, cb) -> status == null ? cb.conjunction()
                : cb.equal(root.get("status"), status);
    }

    public static Specification<JobApplicationEntity> companyLike(String company) {
        return (root, query, cb) -> (company == null || company.isEmpty()) ? cb.conjunction()
                : cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase() + "%");
    }

    public static Specification<JobApplicationEntity> positionLike(String position) {
        return (root, query, cb) -> (position == null || position.isEmpty()) ? cb.conjunction()
                : cb.like(cb.lower(root.get("position")), "%" + position.toLowerCase() + "%");
    }
}
