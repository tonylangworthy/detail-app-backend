package com.webbdealer.detailing.job;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobAction;
import org.springframework.context.ApplicationEvent;

public class JobActionEvent extends ApplicationEvent {

    private final JobAction jobAction;

    public JobActionEvent(Object source, JobAction jobAction) {
        super(source);
        this.jobAction = jobAction;
    }

    public JobAction getJobAction() {
        return jobAction;
    }
}
