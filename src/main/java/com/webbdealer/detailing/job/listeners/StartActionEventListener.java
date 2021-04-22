package com.webbdealer.detailing.job.listeners;

import com.webbdealer.detailing.job.events.StartActionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartActionEventListener {

    @EventListener
    public void handleJobStatusUpdate(StartActionEvent event) {
        System.out.println("EVENT: Job Status updated");
    }

    @EventListener
    public void handleJobActionUpdate(StartActionEvent event) {
        System.out.println("EVENT: Job Action updated");
    }
}
