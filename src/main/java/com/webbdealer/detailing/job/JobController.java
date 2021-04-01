package com.webbdealer.detailing.job;

import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.dao.Action;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobStatus;
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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private JobService jobService;

    private EmployeeService employeeService;

    @Autowired
    public JobController(JobService jobService, EmployeeService employeeService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
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
                jobs = jobService.fetchJobsByStatus(userDetails.getCompanyId(), JobStatus.PENDING);
                break;
            case "active":
                jobs = jobService.fetchJobsByStatus(userDetails.getCompanyId(), JobStatus.ACTIVE);
                break;
            case "completed":
                jobs = jobService.fetchJobsByStatus(userDetails.getCompanyId(), JobStatus.COMPLETED);
                break;
            default:
                jobs = new ArrayList<>();
                break;
        }

        logger.info(jobs.toString());

        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDetailsResponse> viewJobDetails(@PathVariable Long id) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        Job job = jobService.fetchById(userDetails.getCompanyId(), id);

        return ResponseEntity.ok(jobService.fetchJobDetails(userDetails.getCompanyId(), job));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateJobStatus(@PathVariable Long id,
                                             @RequestBody JobActionRequest jobActionRequest) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();
        Long userId = userDetails.getUserId();

        String output = "";
        String jobAction = jobActionRequest.getAction().trim().toLowerCase();
        LocalDateTime actionAt = jobActionRequest.getActionAt();
        System.out.println(jobAction);
        System.out.println(jobActionRequest.getActionAt());

        Job job = jobService.fetchById(userDetails.getCompanyId(), id);
        Optional<User> optionalUser = employeeService.fetchById(userId);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User with id of ["+userId+"] not found!"));

        switch (jobAction) {
            case "start":
                output = "job started";
                jobService.startJob(job, user, actionAt);
                break;
            case "stop":
                output = "job stopped";
                jobService.stopJob(job, user, actionAt);
                break;
            case "pause":
                output = "job paused";
                jobService.pauseJob(job, user, actionAt);
                break;
            case "resume":
                output = "job resumed";
                jobService.resumeJob(job, user, actionAt);
                break;
            case "cancel":
                output = "job canceled";
                jobService.cancelJob(job, user, actionAt);
                break;
            default:
                output = "invalid action!";
                break;
        }


        return ResponseEntity.ok(output);
    }
}
