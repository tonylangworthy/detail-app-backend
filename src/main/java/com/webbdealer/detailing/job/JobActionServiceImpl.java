package com.webbdealer.detailing.job;

import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.dao.*;
import com.webbdealer.detailing.job.events.StartActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    public JobAction logStartAction(Job job, User user, LocalDateTime actionAt) {
        JobAction jobAction = new JobAction(actionAt, Action.START, job, user);
        publisher.publishEvent(new StartActionEvent(this, jobAction));
        return jobAction;
    }

    @Override
    public JobAction logPauseAction(Job job, User user, LocalDateTime actionAt) {

        return new JobAction(actionAt, Action.PAUSE, job, user);
    }

    @Override
    public JobAction logResumeAction(Job job, User user, LocalDateTime actionAt) {
        return new JobAction(actionAt, Action.RESUME, job, user);
    }

    @Override
    public JobAction logFinishAction(Job job, User user, LocalDateTime actionAt) {

        return new JobAction(actionAt, Action.FINISH, job, user);
    }

    @Override
    public JobAction logCancelAction(Job job, User user, LocalDateTime actionAt) {
        return new JobAction(actionAt, Action.CANCEL, job, user);
    }

    @Override
    public JobAction logApprovalAction(Job job, User user, LocalDateTime approvedAt) {
        return new JobAction(approvedAt, Action.APPROVE, job, user);
    }

    @Override
    public JobAction logDenialAction(Job job, User user, LocalDateTime deniedAt) {
        return new JobAction(deniedAt, Action.DENY, job, user);
    }

    @Override
    public JobAction saveJobAction(JobAction jobAction) {
        return jobActionRepository.save(jobAction);
    }

    @Override
    public List<JobAction> fetchJobActionsByJob(Job job) {
        return jobActionRepository.findByJobId(job.getId());
    }

    @Override
    public List<JobAction> fetchJobActionsByEmployee(User user) {
        return jobActionRepository.findByUser(user);
    }

}
