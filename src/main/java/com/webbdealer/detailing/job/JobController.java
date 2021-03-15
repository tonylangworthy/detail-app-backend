package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dto.JobActionRequest;
import com.webbdealer.detailing.job.dto.CreateJobRequest;
import com.webbdealer.detailing.job.dto.JobDetailsResponse;
import com.webbdealer.detailing.job.dto.JobItemResponse;
import com.webbdealer.detailing.security.JwtClaim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("")
    public ResponseEntity<?> createJob(@RequestBody CreateJobRequest createJobRequest) {
        // 1. All form data will be submitted at once.

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        Job job = jobService.storeJobFromRequest(userDetails.getCompanyId(), createJobRequest);

        System.out.println(createJobRequest.toString());

        return ResponseEntity.ok("Okay");
    }

    @GetMapping("")
    public ResponseEntity<?> listJobs(@RequestParam(required = false) String status) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        List<JobItemResponse> jobs;

        if(status == null) {
            jobs = jobService.fetchAllJobs(userDetails.getCompanyId());
            return ResponseEntity.ok(jobs);
        }
        switch (status) {
            case "pending":
                jobs = jobService.fetchPendingJobs(userDetails.getCompanyId());
            case "active":
                jobs = jobService.fetchActiveJobs(userDetails.getCompanyId());
            case "completed":
                jobs = jobService.fetchCompletedJobs(userDetails.getCompanyId());
            default:
                jobs = new ArrayList<>();
        }

        logger.info(jobs.toString());

        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDetailsResponse> viewJobDetails(@PathVariable Long id) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        return ResponseEntity.ok(jobService.fetchJobDetails(userDetails.getCompanyId(), id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateJobStatus(@PathVariable Long id,
                                             @RequestBody JobActionRequest jobActionRequest) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        String output = "";
        String jobAction = jobActionRequest.getAction().trim().toLowerCase();
        LocalDateTime actionAt = jobActionRequest.getActionAt();
        System.out.println(jobAction);
        System.out.println(jobActionRequest.getActionAt());

        switch (jobAction) {
            case "start":
                output = "job started";
                jobService.startJob(id, userDetails.getUserId(), actionAt);
                break;
            case "pause":
                output = "job paused";
                jobService.pauseJob(id, actionAt);
                break;
            case "end":
                output = "job ended";
                jobService.endJob(id, actionAt);
                break;
            case "cancel":
                output = "job canceled";
                jobService.cancelJob(id);
                break;
            default:
                output = "invalid action!";
                break;
        }


        return ResponseEntity.ok(output);
    }
}
