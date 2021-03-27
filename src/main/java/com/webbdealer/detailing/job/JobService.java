package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.job.dto.CreateJobRequest;
import com.webbdealer.detailing.job.dto.JobDetailsResponse;
import com.webbdealer.detailing.job.dto.JobItemResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface JobService {

    Job fetchById(Long id);

    Job fetchByIdReference(Long id);

    List<JobItemResponse> fetchAllJobs(Long companyId);

    List<JobItemResponse> fetchPendingJobs(Long companyId);

    List<JobItemResponse> fetchActiveJobs(Long companyId);

    List<JobItemResponse> fetchCompletedJobs(Long companyId);

    JobDetailsResponse fetchJobDetails(Long companyId, Long jobId);

    Duration jobTotalTime(Long jobId);

    Job storeJobFromRequest(Long companyId, CreateJobRequest createJobRequest);

    Job storeJob(Long companyId, Job job);

}
