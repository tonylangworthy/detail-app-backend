package com.webbdealer.detailing.timeclock.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.webbdealer.detailing.employee.dao.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "timeclock")
public class TimeClock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "clocked_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime clockedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clocked_reason_id", nullable = false)
    private ClockedReason clockedReason;

    @Column(name = "employee_note")
    private String employeeNote;

    @Enumerated
    @Column(name = "clocked_status", columnDefinition = "smallint")
    private ClockedStatus clockedStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public ClockedReason getClockedReason() {
        return clockedReason;
    }

    public void setClockedReason(ClockedReason clockedReason) {
        this.clockedReason = clockedReason;
    }

    public String getEmployeeNote() {
        return employeeNote;
    }

    public void setEmployeeNote(String employeeNote) {
        this.employeeNote = employeeNote;
    }

    public ClockedStatus getClockedStatus() {
        return clockedStatus;
    }

    public void setClockedStatus(ClockedStatus clockedStatus) {
        this.clockedStatus = clockedStatus;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeClock timeClock = (TimeClock) o;
        return Objects.equals(id, timeClock.id) && Objects.equals(clockedAt, timeClock.clockedAt) && Objects.equals(clockedReason, timeClock.clockedReason) && Objects.equals(employeeNote, timeClock.employeeNote) && clockedStatus == timeClock.clockedStatus && Objects.equals(user, timeClock.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clockedAt, clockedReason, employeeNote, clockedStatus, user);
    }

    @Override
    public String toString() {
        return "TimeClock{" +
                "id=" + id +
                ", clockedAt=" + clockedAt +
                ", employeeNote='" + employeeNote + '\'' +
                ", clockedStatus=" + clockedStatus +
                '}';
    }
}
