package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dto.JobCreateForm;
import com.webbdealer.detailing.job.dto.JobResponse;
import com.webbdealer.detailing.security.JwtClaim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<?> createJob(@RequestBody JobCreateForm jobCreateForm) {
        // 1. All form data will be submitted at once.

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        Job job = jobService.storeJobFromRequest(userDetails.getCompanyId(), jobCreateForm);

        System.out.println(jobCreateForm.toString());

        return ResponseEntity.ok("Okay");
    }

    @GetMapping("")
    public ResponseEntity<?> listAllJobs() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        List<JobResponse> jobs = jobService.fetchAllJobs(userDetails.getCompanyId());

        logger.info(jobs.toString());

        return ResponseEntity.ok(jobs);
    }
}