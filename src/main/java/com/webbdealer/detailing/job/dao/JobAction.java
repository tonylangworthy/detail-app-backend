package com.webbdealer.detailing.job.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webbdealer.detailing.employee.dao.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "job_actions")
public class JobAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "job_action_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime jobActionAt;

    @Column(name = "job_action")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonIgnore
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public JobAction() {
    }

    public JobAction(LocalDateTime jobActionAt, Action action, Job job, User user) {
        this.jobActionAt = jobActionAt;
        this.action = action;
        this.job = job;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getJobActionAt() {
        return jobActionAt;
    }

    public void setJobActionAt(LocalDateTime jobActionAt) {
        this.jobActionAt = jobActionAt;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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
        JobAction jobAction = (JobAction) o;
        return Objects.equals(id, jobAction.id) && Objects.equals(jobActionAt, jobAction.jobActionAt) && action == jobAction.action && Objects.equals(job, jobAction.job) && Objects.equals(user, jobAction.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobActionAt, action, job, user);
    }

    @Override
    public String toString() {
        return "JobAction{" +
                "id=" + id +
                ", jobActionAt=" + jobActionAt +
                ", action=" + action +
                ", job=" + job +
                ", user=" + user +
                '}';
    }
}
