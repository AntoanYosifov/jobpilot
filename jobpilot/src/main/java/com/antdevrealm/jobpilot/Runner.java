package com.antdevrealm.jobpilot;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.exception.ExternalServiceException;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.repository.jobapplication.JobApplicationRepository;
import com.antdevrealm.jobpilot.service.JobPostingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Runner.class);
    private final JobApplicationRepository repo;

    private final JobPostingService jobPostingService;

    public Runner(JobApplicationRepository repo, JobPostingService jobPostingService) {
        this.repo = repo;
        this.jobPostingService = jobPostingService;
    }


    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            List<JobApplicationEntity> jobs = List.of(
                    new JobApplicationEntity("Spotify", "Java Developer", StatusEnum.APPLIED, LocalDate.now().minusDays(10)),
                    new JobApplicationEntity("Google", "Backend Engineer", StatusEnum.INTERVIEW, LocalDate.now().minusDays(7)),
                    new JobApplicationEntity("Netflix", "Junior Developer", StatusEnum.REJECTED, LocalDate.now().minusDays(20)),
                    new JobApplicationEntity("Amazon", "Cloud Java Developer", StatusEnum.APPLIED, LocalDate.now().minusDays(3)),
                    new JobApplicationEntity("Meta", "Software Engineer", StatusEnum.OFFER, LocalDate.now().minusDays(15)),
                    new JobApplicationEntity("Apple", "Spring Boot Engineer", StatusEnum.REJECTED, LocalDate.now().minusDays(22)),
                    new JobApplicationEntity("Microsoft", "Java Backend Intern", StatusEnum.APPLIED, LocalDate.now().minusDays(5)),
                    new JobApplicationEntity("Dropbox", "Entry-Level Developer", StatusEnum.INTERVIEW, LocalDate.now().minusDays(11)),
                    new JobApplicationEntity("Oracle", "Java SE Developer", StatusEnum.REJECTED, LocalDate.now().minusDays(30)),
                    new JobApplicationEntity("Adobe", "Junior API Engineer", StatusEnum.APPLIED, LocalDate.now().minusDays(1)),
                    new JobApplicationEntity("Spotify", "Java Developer", StatusEnum.APPLIED, LocalDate.now().minusDays(10)),
                    new JobApplicationEntity("Google", "Backend Engineer", StatusEnum.INTERVIEW, LocalDate.now().minusDays(7)),
                    new JobApplicationEntity("Netflix", "Junior Developer", StatusEnum.REJECTED, LocalDate.now().minusDays(20)),
                    new JobApplicationEntity("Amazon", "Cloud Java Developer", StatusEnum.APPLIED, LocalDate.now().minusDays(3)),
                    new JobApplicationEntity("Meta", "Software Engineer", StatusEnum.OFFER, LocalDate.now().minusDays(15)),
                    new JobApplicationEntity("Apple", "Spring Boot Engineer", StatusEnum.REJECTED, LocalDate.now().minusDays(22)),
                    new JobApplicationEntity("Microsoft", "Java Backend Intern", StatusEnum.APPLIED, LocalDate.now().minusDays(5)),
                    new JobApplicationEntity("Dropbox", "Entry-Level Developer", StatusEnum.INTERVIEW, LocalDate.now().minusDays(11)),
                    new JobApplicationEntity("Oracle", "Java SE Developer", StatusEnum.REJECTED, LocalDate.now().minusDays(30)),
                    new JobApplicationEntity("Adobe", "Junior API Engineer", StatusEnum.APPLIED, LocalDate.now().minusDays(1))
            );

            repo.saveAll(jobs);
            System.out.println("Seeded 20 job applications.");
        }
        try {

            jobPostingService.refreshJobPostings(-1);
        } catch (ExternalServiceException ex) {
            log.error(ex.getMessage(), ex.getCause());
        }
    }
}
