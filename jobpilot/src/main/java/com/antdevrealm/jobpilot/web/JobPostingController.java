package com.antdevrealm.jobpilot.web;


import com.antdevrealm.jobpilot.model.dto.PaginatedResponse;
import com.antdevrealm.jobpilot.model.dto.jobposting.JobPostingResponseDTO;
import com.antdevrealm.jobpilot.service.JobPostingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<JobPostingResponseDTO>> getPostings(
            @RequestParam(required = false) String positionName,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String locationName,
            @PageableDefault(
                    size = 10,
                    sort = "externalCreatedAt",
                    direction = Sort.Direction.DESC) Pageable pageable)  {
        PaginatedResponse<JobPostingResponseDTO> result = jobPostingService.searchJobs(positionName, companyName, locationName, pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.totalElements()))
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostingResponseDTO> getById(@PathVariable Long id) {
        JobPostingResponseDTO postingDTO = jobPostingService.getById(id);
        return ResponseEntity.ok(postingDTO);
    }
}
