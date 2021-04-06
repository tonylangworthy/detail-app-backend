package com.webbdealer.detailing.job;

import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobActionServiceImpl implements JobActionService {

    private ApplicationEventPublisher publisher;

    private JobActionRepository jobActionRepository;

    private EmployeeService employeeService;

    @Autowired
    public JobActionServiceImpl(ApplicationEventPublisher publisher,
                                JobActionRepository jobActionRepository,
                                EmployeeService employeeService) {
        this.publisher = publisher;
        this.jobActionRepository = jobActionRepository;
        this.employeeService = employeeService;
    }

    @Override
    public JobStatus jobStatus(Job job) {

        JobStatus status = null;

//        // If the job_started_at is null, the status is PENDING
//        if (job.getJobStartedAt() == null) {
//            logger.info("JOB IS PENDING");
//            status = JobStatus.PENDING;
//        }
//
//        // If job_started_at is before now and job_ended_at == null, and job_paused_at is null, the job is ACTIVE
//        else if (job.getJobStartedAt() != null && job.getJobEndedAt() == null) {
//            logger.info("JOB IS ACTIVE");
//            status = JobStatus.ACTIVE;
//        }
//
//        // If job_finished === true, the status is COMPLETED
//        else if (job.getJobEndedAt() != null) {
//            logger.info("JOB IS COMPLETED");
//            status = JobStatus.COMPLETED;
//        }
        return status;
    }

    @Override
    public JobAction createStartAction(Job job, User user, LocalDateTime actionAt) {
        return new JobAction(actionAt, Action.START, job, user);
    }

    @Override
    public JobAction createStopAction(Job job, User user, LocalDateTime actionAt) {
        return new JobAction(actionAt, Action.STOP, job, user);
    }

    @Override
    public JobAction saveJobAction(JobAction jobAction) {
        return jobActionRepository.save(jobAction);
    }

    @Override
    public List<JobAction> fetchActionsByJobId(Long jobId) {
        return jobActionRepository.findByJobId(jobId);
    }

    @Override
    public void saveJobStatus(Long jobId, Long userId, LocalDateTime startAt, Action action) {
        List<JobAction> jobActions = fetchActionsByJobId(jobId);

        // 1. Has job been started at all?
        // If no, start job.
        // If yes, has job been started by this user?
        // If no, start job


//        if(job.getJobEndedAt() != null) {
//            throw new InvalidJobActionException("Job already ended!!");
//        }
//        job.setJobStartedAt(startAt.atZone(zoneId));
//        job.getEmployees().add(user);
    }

//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.publisher = publisher;
//    }
}
