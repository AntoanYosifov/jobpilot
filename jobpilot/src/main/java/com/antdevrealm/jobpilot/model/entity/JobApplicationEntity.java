package com.antdevrealm.jobpilot.model.entity;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
@Setter
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
    @Column(nullable = false)
    @ToString.Include

    private String company;

    @NonNull
    @Column(nullable = false)
    @ToString.Include
    private String position;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "applied_on", nullable = false)
    @NonNull
    @ToString.Include
    private LocalDate appliedOn;

    private String feedback;
}
