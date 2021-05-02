package com.webbdealer.detailing.timeclock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webbdealer.detailing.shared.UserTimeZoneDeserializer;
import com.webbdealer.detailing.shared.UserTimeZoneSerializer;
import com.webbdealer.detailing.timeclock.dao.ClockedStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public class TimeClockRequest implements Serializable {

    private Long userId;

    private Long clockedReasonId;

    private ClockedStatus clockedStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm a")
    @JsonDeserialize(using = UserTimeZoneDeserializer.class)
    private LocalDateTime clockedAt;

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

    public LocalDateTime getClockedAt() {
        return clockedAt;
    }

    public void setClockedAt(LocalDateTime clockedAt) {
        this.clockedAt = clockedAt;
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
        return Objects.equals(userId, that.userId) && Objects.equals(clockedReasonId, that.clockedReasonId) && clockedStatus == that.clockedStatus && Objects.equals(clockedAt, that.clockedAt) && Objects.equals(employeeNote, that.employeeNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, clockedReasonId, clockedStatus, clockedAt, employeeNote);
    }

    @Override
    public String toString() {
        return "TimeClockRequest{" +
                "userId=" + userId +
                ", clockedReasonId=" + clockedReasonId +
                ", clockedStatus=" + clockedStatus +
                ", clockedAt='" + clockedAt + '\'' +
                ", employeeNote='" + employeeNote + '\'' +
                '}';
    }
}
