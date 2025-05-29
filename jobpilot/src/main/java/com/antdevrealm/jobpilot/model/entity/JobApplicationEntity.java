package com.antdevrealm.jobpilot.model.entity;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "job_applications")
public class JobApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @ToString.Include
    @Column(nullable = false)
    private String company;

    @NonNull
    @ToString.Include
    @Column(nullable = false)
    private String position;

    // TODO: Add a field for the AI generated specific cover letter based on the user's profile and job posting details

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @NonNull
    @ToString.Include
    @Column(name = "applied_on", nullable = false)
    @EqualsAndHashCode.Include
    private LocalDate appliedOn;

    private String feedback;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "target_job")
    private JobPostingEntity targetJob;

    public JobApplicationEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public JobApplicationEntity setCompany(@NonNull String company) {
        this.company = company;
        return this;
    }

    public JobApplicationEntity setPosition(@NonNull String position) {
        this.position = position;
        return this;
    }

    public JobApplicationEntity setStatus(@NonNull StatusEnum status) {
        this.status = status;
        return this;
    }

    public JobApplicationEntity setAppliedOn(@NonNull LocalDate appliedOn) {
        this.appliedOn = appliedOn;
        return this;
    }

    public JobApplicationEntity setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public JobApplicationEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public JobApplicationEntity setTargetJob(JobPostingEntity targetJob) {
        this.targetJob = targetJob;
        return this;
    }
}
