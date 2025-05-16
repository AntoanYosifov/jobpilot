package com.antdevrealm.jobpilot.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "job_postings")
public class JobPostingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(nullable = false, name = "external_id", unique = true, updatable = false)
    private Long externalId;

    @NonNull
    @Column(nullable = false)
    @ToString.Include
    private String title;

    @NonNull
    @Column(nullable = false)
    @ToString.Include
    private String companyName;

    @NonNull
    @Column(nullable = false)
    private String locationName;

    @NonNull
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NonNull
    @Column(nullable = false)
    private String redirect_url;

    @NonNull
    @Column(nullable = false, name = "external_created_at")
    private ZonedDateTime externalCreatedAt;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at", updatable = false)
    private Instant createdAt;
}
