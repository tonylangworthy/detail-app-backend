package com.webbdealer.detailing.job.events;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobAction;
import org.springframework.context.ApplicationEvent;

public class StartActionEvent extends ApplicationEvent {

    private final JobAction jobAction;

    public StartActionEvent(Object source, JobAction jobAction) {
        super(source);
        this.jobAction = jobAction;
    }

    public JobAction getJobAction() {
        return jobAction;
    }
}
