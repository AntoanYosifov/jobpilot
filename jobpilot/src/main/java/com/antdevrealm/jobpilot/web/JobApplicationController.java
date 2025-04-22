package com.antdevrealm.jobpilot.web;

import com.antdevrealm.jobpilot.enums.StatusEnum;
import com.antdevrealm.jobpilot.model.dto.JobApplicationDTO;
import com.antdevrealm.jobpilot.model.dto.JobApplicationResponseDTO;
import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @GetMapping
    public ResponseEntity<PaginatedResponse<JobApplicationResponseDTO>> getAll (
    @RequestParam(required = false) String status,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size) {

        int maxSize = 50;
        page = Math.max(0, page);
        size = Math.min(Math.max(1, size), maxSize);

        PaginatedResponse<JobApplicationResponseDTO> response;
        if(status != null) {
            StatusEnum statusEnum;
            try {
                statusEnum = StatusEnum.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                List<String> validStatuses = Arrays.stream(StatusEnum.values()).map(Enum::name)
                        .toList();

                throw new com.antdevrealm.jobpilot.exception.BadRequestException("Invalid status value: " + status, validStatuses);
            }
            response = jobService.getByStatus(statusEnum, page, size);
        } else {
            response = jobService.getAll(page, size);
        }

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(response.totalElements()))
                .body(response);
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
