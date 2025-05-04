package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.service.JobApplicationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/applications")
@Validated
public class JobApplicationController {
    private final JobApplicationService jobService;

    @Autowired
    public JobApplicationController(JobApplicationService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<JobApplicationResponseDTO>> getApplications(
            @RequestParam(required = false) StatusEnum status,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String positionName,
            @RequestParam(defaultValue = "desc") @Pattern(regexp = "asc|desc") String sortDir,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) @Max(50) int size) {

        PaginatedResponse<JobApplicationResponseDTO> result = jobService.searchApplications(
                status, companyName, positionName, sortDir, page, size);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.totalElements()))
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> getById(@PathVariable Long id) {
        JobApplicationResponseDTO jobAppById = jobService.getById(id);
        return ResponseEntity.ok(jobAppById);
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> create(@RequestBody @Valid JobApplicationDTO dto) {
        JobApplicationResponseDTO savedApp = jobService.apply(dto);
        URI location = URI.create("/api/applications/" + savedApp.id());
        return ResponseEntity.created(location)
                .body(savedApp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> updateById(@PathVariable Long id, @RequestBody JobApplicationDTO dto) {
        JobApplicationResponseDTO updated = jobService.updateById(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        jobService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
