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

    private JobService jobService;

    @Autowired
    public JobActionServiceImpl(ApplicationEventPublisher publisher,
                                JobActionRepository jobActionRepository,
                                EmployeeService employeeService,
                                JobService jobService) {
        this.publisher = publisher;
        this.jobActionRepository = jobActionRepository;
        this.employeeService = employeeService;
        this.jobService = jobService;
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
    public List<JobAction> fetchActionsByJobId(Long jobId) {
        return jobActionRepository.findByJobId(jobId);
    }

    @Override
    public void updateJobStatus(Long jobId, Long userId, LocalDateTime startAt, Action action) {
        List<JobAction> jobActions = fetchActionsByJobId(jobId);

        // 1. Has job been started at all?
        // If no, start job.
        // If yes, has job been started by this user?
        // If no, start job

        User user = employeeService.fetchByIdReference(userId);
        Job job = jobService.fetchByIdReference(jobId);

        JobAction jobAction = new JobAction(LocalDateTime.now(), Action.START, job, user);

//        if(job.getJobEndedAt() != null) {
//            throw new InvalidJobActionException("Job already ended!!");
//        }
//        job.setJobStartedAt(startAt.atZone(zoneId));
//        job.getEmployees().add(user);
        JobAction savedJobAction = jobActionRepository.save(jobAction);
        publisher.publishEvent(new JobActionEvent(this, savedJobAction));
    }

//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.publisher = publisher;
//    }
}
