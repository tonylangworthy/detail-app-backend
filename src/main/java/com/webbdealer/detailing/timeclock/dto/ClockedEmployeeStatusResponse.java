package com.webbdealer.detailing.timeclock.dto;

import com.webbdealer.detailing.timeclock.dao.ClockedStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ClockedEmployeeStatusResponse implements Serializable {

    private Long id;

    private LocalDateTime clockedAt;

    private ClockedStatus clockedStatus;

    private String clockedReason;

    private String employeeNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getClockedAt() {
        return clockedAt;
    }

    public void setClockedAt(LocalDateTime clockedAt) {
        this.clockedAt = clockedAt;
    }

    public ClockedStatus getClockedStatus() {
        return clockedStatus;
    }

    public void setClockedStatus(ClockedStatus clockedStatus) {
        this.clockedStatus = clockedStatus;
    }

    public String getClockedReason() {
        return clockedReason;
    }

    public void setClockedReason(String clockedReason) {
        this.clockedReason = clockedReason;
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
        ClockedEmployeeStatusResponse that = (ClockedEmployeeStatusResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(clockedAt, that.clockedAt) && clockedStatus == that.clockedStatus && Objects.equals(clockedReason, that.clockedReason) && Objects.equals(employeeNote, that.employeeNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clockedAt, clockedStatus, clockedReason, employeeNote);
    }

    @Override
    public String toString() {
        return "ClockedEmployeeStatusResponse{" +
                "id=" + id +
                ", clockedAt=" + clockedAt +
                ", clockedStatus=" + clockedStatus +
                ", clockedReason='" + clockedReason + '\'' +
                ", employeeNote='" + employeeNote + '\'' +
                '}';
    }
}
