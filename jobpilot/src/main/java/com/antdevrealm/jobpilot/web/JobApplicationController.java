package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.jobapplication.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.security.model.JobPilotUserDetails;
import com.antdevrealm.jobpilot.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @PageableDefault(
                    size = 5,
                    sort = "appliedOn",
                    direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal JobPilotUserDetails principal) {

        PaginatedResponse<JobApplicationResponseDTO> result = jobService.searchApplications(principal.getId(),
                status, companyName, positionName, pageable);

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
    public ResponseEntity<JobApplicationResponseDTO> create(@RequestBody @Valid JobApplicationDTO dto,
                                                            @AuthenticationPrincipal JobPilotUserDetails principal) {
        JobApplicationResponseDTO savedApp = jobService.apply(dto, principal.getId());
        URI location = URI.create("/api/applications/" + savedApp.id());
        return ResponseEntity.created(location)
                .body(savedApp);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        jobService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
