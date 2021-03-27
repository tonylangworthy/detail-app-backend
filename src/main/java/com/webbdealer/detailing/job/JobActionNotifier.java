package com.webbdealer.detailing.job;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class JobActionNotifier implements ApplicationListener<JobActionEvent> {



    @Override
    public void onApplicationEvent(JobActionEvent jobActionEvent) {
        System.out.println("EVENT: Job " + jobActionEvent.getJobAction().getAction() + " at " + jobActionEvent.getJobAction().getJobActionAt());
    }
}
