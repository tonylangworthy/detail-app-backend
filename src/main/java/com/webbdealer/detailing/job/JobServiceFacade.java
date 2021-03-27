package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.job.dto.CreateJobRequest;
import com.webbdealer.detailing.job.dto.JobDetailsResponse;
import com.webbdealer.detailing.job.dto.JobItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface JobServiceFacade {

    void startJob(Long jobId, Long userId, LocalDateTime startAt);

    void stopJob(Long jobId, Long userId, LocalDateTime stopAt);

    void pauseJob(Long jobId, Long userId, LocalDateTime pauseAt);

    void resumeJob(Long jobId, Long userId, LocalDateTime resumeAt);

    void cancelJob(Long jobId, Long userId, LocalDateTime cancelAt);

    Job storeJobFromRequest(Long companyId, CreateJobRequest createJobRequest);

    List<JobItemResponse> fetchAllJobs(Long companyId);

    List<JobItemResponse> fetchJobs(Long companyId, JobStatus jobStatus);

    JobDetailsResponse fetchJobDetails(Long companyId, Long jobId);
}
