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
    @ToString.Include
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String company;

    @NonNull
    @ToString.Include
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String position;

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
}
