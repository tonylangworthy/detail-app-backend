package com.webbdealer.detailing.job.listeners;

import com.webbdealer.detailing.job.events.StartActionEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class JobActionNotifier implements ApplicationListener<StartActionEvent> {



    @Override
    public void onApplicationEvent(StartActionEvent startActionEvent) {
        System.out.println("EVENT: Job " + startActionEvent.getJobAction().getAction() + " at " + startActionEvent.getJobAction().getJobActionAt());
    }
}
