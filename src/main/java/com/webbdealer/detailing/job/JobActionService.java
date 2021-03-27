package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Action;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobAction;
import com.webbdealer.detailing.job.dao.JobStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface JobActionService {

    JobStatus jobStatus(Job job);

    List<JobAction> fetchActionsByJobId(Long jobId);

    void updateJobStatus(Long jobId, Long userId, LocalDateTime actionAt, Action action);

}
