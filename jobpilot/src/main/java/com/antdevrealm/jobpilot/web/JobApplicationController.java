package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService jobService;

    @Autowired
    public JobApplicationController(JobApplicationService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationResponseDTO>> getAll () {
        List<JobApplicationResponseDTO> allJobApp = jobService.getAll();
        return ResponseEntity.ok(allJobApp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> getById(@PathVariable Long id) {
        JobApplicationResponseDTO jobAppById = jobService.getById(id);
        return ResponseEntity.ok(jobAppById);

    }

    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> create(@RequestBody JobApplicationDTO dto) {
        JobApplicationResponseDTO savedApp = jobService.apply(dto);
        URI location = URI.create("/api/applications/" + savedApp.id());
        return ResponseEntity.created(location)
                .body(savedApp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        jobService.deleteById(id);

        return ResponseEntity.noContent().build();

    }
}
