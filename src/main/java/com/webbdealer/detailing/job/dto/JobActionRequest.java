package com.webbdealer.detailing.job.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webbdealer.detailing.job.dao.Action;

import java.io.Serializable;
import java.time.LocalDateTime;

public class JobActionRequest implements Serializable {

    private String action;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm a")
    private LocalDateTime actionAt;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getActionAt() {
        return actionAt;
    }

    public void setActionAt(LocalDateTime actionAt) {
        this.actionAt = actionAt;
    }
}
