package com.antdevrealm.jobpilot;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Runner implements CommandLineRunner {

    private final JobApplicationRepository jobRepo;

    @Autowired
    public Runner(JobApplicationRepository jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Override
    public void run(String... args) throws Exception {
//        JobApplicationEntity testJob = new JobApplicationEntity("Google", "Junior Java Backend developer", StatusEnum.APPLIED, LocalDate.now());
//
//        JobApplicationEntity saved = jobRepo.save(testJob);
//
//        System.out.println(saved);
    }
}
