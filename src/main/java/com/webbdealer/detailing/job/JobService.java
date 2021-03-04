package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.job.dto.JobCreateForm;
import com.webbdealer.detailing.job.dto.JobResponse;

import java.util.List;
import java.util.Optional;

public interface JobService {

    Job fetchById(Long id);

    List<JobResponse> fetchAllJobs(Long companyId);

    List<JobResponse> fetchPendingJobs(Long companyId);

    List<JobResponse> fetchActiveJobs(Long companyId);

    List<JobResponse> fetchCompletedJobs(Long companyId);

    Job storeJobFromRequest(Long companyId, JobCreateForm jobCreateForm);

    Job storeJob(Long companyId, JobCreateForm jobCreateForm);

    JobStatus jobStatus(Job job);

    void startJob(Long id);

    void pauseJob(Long id);

    void endJob(Long id);

    void cancelJob(Long id);
}
