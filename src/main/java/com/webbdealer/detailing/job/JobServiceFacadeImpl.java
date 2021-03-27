package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Action;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.job.dto.CreateJobRequest;
import com.webbdealer.detailing.job.dto.JobDetailsResponse;
import com.webbdealer.detailing.job.dto.JobItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobServiceFacadeImpl implements JobServiceFacade {

    private JobService jobService;

    private JobActionService jobActionService;

    @Autowired
    public JobServiceFacadeImpl(JobService jobService, JobActionService jobActionService) {
        this.jobService = jobService;
        this.jobActionService = jobActionService;
    }

    @Override
    public void startJob(Long jobId, Long userId, LocalDateTime startAt) {
        jobActionService.updateJobStatus(jobId, userId, startAt, Action.START);
    }

    @Override
    public void stopJob(Long jobId, Long userId, LocalDateTime stopAt) {
        jobActionService.updateJobStatus(jobId, userId, stopAt, Action.STOP);
    }

    @Override
    public void pauseJob(Long jobId, Long userId, LocalDateTime pauseAt) {
        jobActionService.updateJobStatus(jobId, userId, pauseAt, Action.PAUSE);
    }

    @Override
    public void resumeJob(Long jobId, Long userId, LocalDateTime resumeAt) {
        jobActionService.updateJobStatus(jobId, userId, resumeAt, Action.RESUME);
    }

    @Override
    public void cancelJob(Long jobId, Long userId, LocalDateTime cancelAt) {
        jobActionService.updateJobStatus(jobId, userId, cancelAt, Action.CANCEL);
    }

    @Override
    public Job storeJobFromRequest(Long companyId, CreateJobRequest createJobRequest) {
        return null;
    }

    @Override
    public List<JobItemResponse> fetchAllJobs(Long companyId) {
        return null;
    }

    @Override
    public List<JobItemResponse> fetchJobs(Long companyId, JobStatus jobStatus) {
        return null;
    }

    @Override
    public JobDetailsResponse fetchJobDetails(Long companyId, Long jobId) {
        return null;
    }
}
