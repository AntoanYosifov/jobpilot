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
    private long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @ToString.Include
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @ToString.Include
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<JobApplicationEntity> jobApplications;

}
