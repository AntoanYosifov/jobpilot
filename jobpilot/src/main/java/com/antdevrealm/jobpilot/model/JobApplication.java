package com.antdevrealm.jobpilot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Will refactor when the actual structure (e.g. Interview entity, enums, etc.) becomes clear.
    private String company;

    private String position;

    private String status;

    @Column(name = "applied_on")
    private String appliedOn;

    private String feedback;

}
