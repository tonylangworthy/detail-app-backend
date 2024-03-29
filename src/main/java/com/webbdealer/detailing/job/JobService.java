package com.webbdealer.detailing.job;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.job.dto.CreateJobRequest;
import com.webbdealer.detailing.job.dto.JobDetailsResponse;
import com.webbdealer.detailing.job.dto.JobItemResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface JobService {

    Job fetchById(Long companyId, Long id);

    Job fetchByIdReference(Long id);

    List<JobItemResponse> fetchAllJobs(Long companyId);

    List<JobItemResponse> fetchJobsByStatus(Long companyId, JobStatus jobStatus);

    JobDetailsResponse fetchJobDetails(Long companyId, Job job);

    Job startJob(Job job, User user, LocalDateTime startAt);

    Job approveJob(Job job, User user, LocalDateTime approvedAt);

    Job denyJob(Job job, User user, LocalDateTime deniedAt);

    Job markJobAsFinished(Job job, User user, LocalDateTime stopAt);

    Job pauseJob(Job job, User user, LocalDateTime pauseAt);

    Job resumeJob(Job job, User user, LocalDateTime resumeAt);

    Job cancelJob(Job job, User user, LocalDateTime cancelAt);

    Duration jobTotalTime(Job job);

    Job storeJobFromRequest(Long companyId, CreateJobRequest createJobRequest);

    Job storeJob(Long companyId, Job job);

    Job storeJobStatus(Job job);

}
