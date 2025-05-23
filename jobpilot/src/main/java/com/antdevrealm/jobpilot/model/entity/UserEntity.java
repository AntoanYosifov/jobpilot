package com.antdevrealm.jobpilot.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @ToString.Include
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @ToString.Include
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "author")
    @Column(name = "job_applications")
    private List<JobApplicationEntity> jobApplications;

}
