package com.webbdealer.detailing.job;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.dao.Action;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobAction;
import com.webbdealer.detailing.job.dao.JobStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface JobActionService {

    JobAction logStartAction(Job job, User user, LocalDateTime actionAt);

    JobAction logStopAction(Job job, User user, LocalDateTime actionAt);

    JobAction logPauseAction(Job job, User user, LocalDateTime actionAt);

    JobAction logResumeAction(Job job, User user, LocalDateTime actionAt);

    JobAction logFinishAction(Job job, User user, LocalDateTime actionAt);

    JobAction logCancelAction(Job job, User user, LocalDateTime actionAt);

    JobAction logApprovalAction(Job job, User user, LocalDateTime approvedAt);

    JobAction logDenialAction(Job job, User user, LocalDateTime deniedAt);

    JobAction saveJobAction(JobAction jobAction);

    List<JobAction> fetchJobActionsByJob(Job job);

}
