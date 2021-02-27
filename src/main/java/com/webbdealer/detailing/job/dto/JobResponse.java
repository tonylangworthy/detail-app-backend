package com.webbdealer.detailing.job.dto;

import com.webbdealer.detailing.customer.dto.CustomerResponse;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.recondition.dto.ReconditionServiceResponse;
import com.webbdealer.detailing.vehicle.dto.VehicleResponse;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class JobResponse implements Serializable {

    private Long id;

    private JobStatus status;

    private String jobStartedAt;

    private String jobPausedAt;

    private String jobEndedAt;

    private boolean canceled;

    private String employeeNotes;

    private String managerNotes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CustomerResponse customer;

    private VehicleResponse vehicle;

    private List<ReconditionServiceResponse> services;

    private List<EmployeeResponse> employees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getJobStartedAt() {
        return jobStartedAt;
    }

    public void setJobStartedAt(String jobStartedAt) {
        this.jobStartedAt = jobStartedAt;
    }

    public String getJobPausedAt() {
        return jobPausedAt;
    }

    public void setJobPausedAt(String jobPausedAt) {
        this.jobPausedAt = jobPausedAt;
    }

    public String getJobEndedAt() {
        return jobEndedAt;
    }

    public void setJobEndedAt(String jobEndedAt) {
        this.jobEndedAt = jobEndedAt;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getEmployeeNotes() {
        return employeeNotes;
    }

    public void setEmployeeNotes(String employeeNotes) {
        this.employeeNotes = employeeNotes;
    }

    public String getManagerNotes() {
        return managerNotes;
    }

    public void setManagerNotes(String managerNotes) {
        this.managerNotes = managerNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public VehicleResponse getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleResponse vehicle) {
        this.vehicle = vehicle;
    }

    public List<ReconditionServiceResponse> getServices() {
        return services;
    }

    public void setServices(List<ReconditionServiceResponse> services) {
        this.services = services;
    }

    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponse> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobResponse that = (JobResponse) o;
        return canceled == that.canceled && Objects.equals(id, that.id) && status == that.status && Objects.equals(jobStartedAt, that.jobStartedAt) && Objects.equals(jobPausedAt, that.jobPausedAt) && Objects.equals(jobEndedAt, that.jobEndedAt) && Objects.equals(employeeNotes, that.employeeNotes) && Objects.equals(managerNotes, that.managerNotes) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(customer, that.customer) && Objects.equals(vehicle, that.vehicle) && Objects.equals(services, that.services) && Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, jobStartedAt, jobPausedAt, jobEndedAt, canceled, employeeNotes, managerNotes, createdAt, updatedAt, customer, vehicle, services, employees);
    }

    @Override
    public String toString() {
        return "JobResponse{" +
                "id=" + id +
                ", status=" + status +
                ", jobStartedAt='" + jobStartedAt + '\'' +
                ", jobPausedAt='" + jobPausedAt + '\'' +
                ", jobEndedAt='" + jobEndedAt + '\'' +
                ", canceled=" + canceled +
                ", employeeNotes='" + employeeNotes + '\'' +
                ", managerNotes='" + managerNotes + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", customer=" + customer +
                ", vehicle=" + vehicle +
                ", services=" + services +
                ", employees=" + employees +
                '}';
    }
}
