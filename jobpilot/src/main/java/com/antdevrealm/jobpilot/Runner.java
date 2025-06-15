package com.antdevrealm.jobpilot;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.exception.ExternalServiceException;
import com.antdevrealm.jobpilot.exception.ResourceNotFoundException;
import com.antdevrealm.jobpilot.model.entity.JobApplicationEntity;
import com.antdevrealm.jobpilot.model.entity.UserEntity;
import com.antdevrealm.jobpilot.repository.jobapplication.JobApplicationRepository;
import com.antdevrealm.jobpilot.repository.user.UserRepository;
import com.antdevrealm.jobpilot.service.JobPostingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Runner.class);
    private final JobApplicationRepository jobApplicationRepo;
    private final UserRepository userRepo;


    private final JobPostingService jobPostingService;

    public Runner(JobApplicationRepository jobApplicationRepo, UserRepository userRepo, JobPostingService jobPostingService) {

        this.jobApplicationRepo = jobApplicationRepo;
        this.userRepo = userRepo;
        this.jobPostingService = jobPostingService;
    }


    @Override
    public void run(String... args) {
//        UserEntity author1 = userRepo.findByEmail("test@mail.com").orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        UserEntity author2 = userRepo.findByEmail("test2@mail.com").orElseThrow(() -> new ResourceNotFoundException("User not found"));

//        if (jobApplicationRepo.count() == 0) {
//            List<JobApplicationEntity> jobs = List.of(
//                    new JobApplicationEntity("Spotify", "Java Developer", StatusEnum.APPLIED, LocalDate.now().minusDays(10)).setAuthor(author1),
//                    new JobApplicationEntity("Google", "Backend Engineer", StatusEnum.INTERVIEW, LocalDate.now().minusDays(7)).setAuthor(author1),
//                    new JobApplicationEntity("Netflix", "Junior Developer", StatusEnum.REJECTED, LocalDate.now().minusDays(20)).setAuthor(author1),
//                    new JobApplicationEntity("Amazon", "Cloud Java Developer", StatusEnum.APPLIED, LocalDate.now().minusDays(3)).setAuthor(author1),
//                    new JobApplicationEntity("Meta", "Software Engineer", StatusEnum.OFFER, LocalDate.now().minusDays(15)).setAuthor(author1),
//                    new JobApplicationEntity("Apple", "Spring Boot Engineer", StatusEnum.REJECTED, LocalDate.now().minusDays(22)).setAuthor(author1),
//                    new JobApplicationEntity("Microsoft", "Java Backend Intern", StatusEnum.APPLIED, LocalDate.now().minusDays(5)).setAuthor(author1),
//                    new JobApplicationEntity("Dropbox", "Entry-Level Developer", StatusEnum.INTERVIEW, LocalDate.now().minusDays(11)).setAuthor(author1),
//                    new JobApplicationEntity("Oracle", "Java SE Developer", StatusEnum.REJECTED, LocalDate.now().minusDays(30)).setAuthor(author1),
//                    new JobApplicationEntity("Adobe", "Junior API Engineer", StatusEnum.APPLIED, LocalDate.now().minusDays(1)).setAuthor(author1),
//                    new JobApplicationEntity("Spotify", "Java Developer", StatusEnum.APPLIED, LocalDate.now().minusDays(10)).setAuthor(author1),
//                    new JobApplicationEntity("Google", "Backend Engineer", StatusEnum.INTERVIEW, LocalDate.now().minusDays(7)).setAuthor(author2),
//                    new JobApplicationEntity("Netflix", "Junior Developer", StatusEnum.REJECTED, LocalDate.now().minusDays(20)).setAuthor(author2),
//                    new JobApplicationEntity("Amazon", "Cloud Java Developer", StatusEnum.APPLIED, LocalDate.now().minusDays(3)).setAuthor(author2),
//                    new JobApplicationEntity("Meta", "Software Engineer", StatusEnum.OFFER, LocalDate.now().minusDays(15)).setAuthor(author2),
//                    new JobApplicationEntity("Apple", "Spring Boot Engineer", StatusEnum.REJECTED, LocalDate.now().minusDays(22)).setAuthor(author2),
//                    new JobApplicationEntity("Microsoft", "Java Backend Intern", StatusEnum.APPLIED, LocalDate.now().minusDays(5)).setAuthor(author2),
//                    new JobApplicationEntity("Dropbox", "Entry-Level Developer", StatusEnum.INTERVIEW, LocalDate.now().minusDays(11)).setAuthor(author2),
//                    new JobApplicationEntity("Oracle", "Java SE Developer", StatusEnum.REJECTED, LocalDate.now().minusDays(30)).setAuthor(author2),
//                    new JobApplicationEntity("Adobe", "Junior API Engineer", StatusEnum.APPLIED, LocalDate.now().minusDays(1)).setAuthor(author2)
//            );
//
//            jobApplicationRepo.saveAll(jobs);
//            System.out.println("Seeded 20 job applications.");
//        }
        try {
            jobPostingService.refreshJobPostings();
        } catch (ExternalServiceException ex) {
            log.error(ex.getMessage(), ex.getCause());
        }
    }
}
