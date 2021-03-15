package com.webbdealer.detailing.timeclock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webbdealer.detailing.timeclock.dao.ClockedStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class TimeClockRequest implements Serializable {

    private Long userId;

    private Long clockedReasonId;

    private ClockedStatus clockedStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate clockedAtDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
    private LocalTime clockedAtTime;

    private String employeeNote;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClockedReasonId() {
        return clockedReasonId;
    }

    public void setClockedReasonId(Long clockedReasonId) {
        this.clockedReasonId = clockedReasonId;
    }

    public ClockedStatus getClockedStatus() {
        return clockedStatus;
    }

    public void setClockedStatus(ClockedStatus clockedStatus) {
        this.clockedStatus = clockedStatus;
    }

    public LocalDate getClockedAtDate() {
        return clockedAtDate;
    }

    public void setClockedAtDate(LocalDate clockedAtDate) {
        this.clockedAtDate = clockedAtDate;
    }

    public LocalTime getClockedAtTime() {
        return clockedAtTime;
    }

    public void setClockedAtTime(LocalTime clockedAtTime) {
        this.clockedAtTime = clockedAtTime;
    }

    public String getEmployeeNote() {
        return employeeNote;
    }

    public void setEmployeeNote(String employeeNote) {
        this.employeeNote = employeeNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeClockRequest that = (TimeClockRequest) o;
        return Objects.equals(userId, that.userId) && Objects.equals(clockedReasonId, that.clockedReasonId) && clockedStatus == that.clockedStatus && Objects.equals(clockedAtDate, that.clockedAtDate) && Objects.equals(clockedAtTime, that.clockedAtTime) && Objects.equals(employeeNote, that.employeeNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, clockedReasonId, clockedStatus, clockedAtDate, clockedAtTime, employeeNote);
    }

    @Override
    public String toString() {
        return "TimeClockRequest{" +
                "userId=" + userId +
                ", clockedReasonId=" + clockedReasonId +
                ", clockedStatus=" + clockedStatus +
                ", clockedAtDate='" + clockedAtDate + '\'' +
                ", clockedAtTime='" + clockedAtTime + '\'' +
                ", employeeNote='" + employeeNote + '\'' +
                '}';
    }
}
