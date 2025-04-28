package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.enums.StatusEnum;

import com.antdevrealm.jobpilot.exception.BadRequestException;
import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.service.JobApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService jobService;

    @Autowired
    public JobApplicationController(JobApplicationService jobService) {
        this.jobService = jobService;
    }


    // TODO: Refine logic. Test!
    @GetMapping
    public ResponseEntity<PaginatedResponse<JobApplicationResponseDTO>> getApplications(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String positionName,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws BadRequestException {

        if (page < 0) throw new BadRequestException("Page must be 0 or higher");
        if (size < 1 || size > 50) throw new BadRequestException("Size must be between 1 and 50");

        StatusEnum statusEnum = null;
        if (status != null) {
            try {
                statusEnum = StatusEnum.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                List<String> validStatuses = Arrays.stream(StatusEnum.values())
                        .map(Enum::name)
                        .toList();
                throw new BadRequestException("Invalid status: " + status, validStatuses);
            }
        }

        PaginatedResponse<JobApplicationResponseDTO> result = jobService.searchApplications(
                statusEnum, companyName, positionName, sortDir, page, size);

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
    public ResponseEntity<JobApplicationResponseDTO> create(@RequestBody JobApplicationDTO dto) {
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
