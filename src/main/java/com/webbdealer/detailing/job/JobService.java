package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.job.dto.JobCreateForm;
import com.webbdealer.detailing.job.dto.JobItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface JobService {

    Job fetchById(Long id);

    Job fetchByIdReference(Long id);

    List<JobItemResponse> fetchAllJobs(Long companyId);

    List<JobItemResponse> fetchPendingJobs(Long companyId);

    List<JobItemResponse> fetchActiveJobs(Long companyId);

    List<JobItemResponse> fetchCompletedJobs(Long companyId);

    Job storeJobFromRequest(Long companyId, JobCreateForm jobCreateForm);

    Job storeJob(Long companyId, Job job);

    JobStatus jobStatus(Job job);

    Job startJob(Long jobId, Long userId, LocalDateTime startAt);

    Job pauseJob(Long jobId, LocalDateTime pauseAt);

    Job endJob(Long jobId, LocalDateTime endAt);

    Job cancelJob(Long jobId);
}
