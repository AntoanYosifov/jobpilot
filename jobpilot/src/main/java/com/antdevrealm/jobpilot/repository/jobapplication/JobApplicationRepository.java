package com.antdevrealm.jobpilot.repository.jobapplication;

import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Long>, JobApplicationRepositoryCustom {
    /**
     * Spring Data repositories are non-null by default due to the @NonNullApi package-level annotation.
     * This method is explicitly marked with {@link org.springframework.lang.NonNull}
     * to satisfy static analysis tools and prevent IDE warnings.
     * <p>
     * Note: {@code @NonNull} used here is from Spring, not Lombok.
     */
    @NonNull
    Page<JobApplicationEntity> findAll(@NonNull Pageable pageable);


}
