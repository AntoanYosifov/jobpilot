package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService jobService;

    @Autowired
    public JobApplicationController(JobApplicationService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> create(@RequestBody JobApplicationDTO dto) {
        JobApplicationResponseDTO saved = jobService.apply(dto);
        URI location = URI.create("/api/applications/" + saved.id());
        return ResponseEntity.created(location)
                .body(saved);
    }
}
