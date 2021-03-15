package com.webbdealer.detailing.job.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webbdealer.detailing.customer.dto.CustomerCreateForm;
import com.webbdealer.detailing.employee.dto.EmployeeCreateForm;
import com.webbdealer.detailing.vehicle.dto.VehicleCreateForm;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CreateJobRequest implements Serializable {

    private Long id;

    private CustomerCreateForm customer;

    private EmployeeCreateForm employee;

    private List<Long> serviceIds;

    private VehicleCreateForm vehicle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm a")
    private LocalDateTime jobStartedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm a")
    private LocalDateTime jobPausedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm a")
    private LocalDateTime jobEndedAt;

    private boolean jobEnded;

    private String managerNotes;

    private String employeeNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerCreateForm getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerCreateForm customer) {
        this.customer = customer;
    }

    public EmployeeCreateForm getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeCreateForm employee) {
        this.employee = employee;
    }

    public VehicleCreateForm getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleCreateForm vehicle) {
        this.vehicle = vehicle;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public LocalDateTime getJobStartedAt() {
        return jobStartedAt;
    }

    public void setJobStartedAt(LocalDateTime jobStartedAt) {
        this.jobStartedAt = jobStartedAt;
    }

    public LocalDateTime getJobPausedAt() {
        return jobPausedAt;
    }

    public void setJobPausedAt(LocalDateTime jobPausedAt) {
        this.jobPausedAt = jobPausedAt;
    }

    public LocalDateTime getJobEndedAt() {
        return jobEndedAt;
    }

    public void setJobEndedAt(LocalDateTime jobEndedAt) {
        this.jobEndedAt = jobEndedAt;
    }

    public boolean isJobEnded() {
        return jobEnded;
    }

    public void setJobEnded(boolean jobEnded) {
        this.jobEnded = jobEnded;
    }

    public String getManagerNotes() {
        return managerNotes;
    }

    public void setManagerNotes(String managerNotes) {
        this.managerNotes = managerNotes;
    }

    public String getEmployeeNotes() {
        return employeeNotes;
    }

    public void setEmployeeNotes(String employeeNotes) {
        this.employeeNotes = employeeNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateJobRequest that = (CreateJobRequest) o;
        return jobEnded == that.jobEnded && Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(employee, that.employee) && Objects.equals(serviceIds, that.serviceIds) && Objects.equals(vehicle, that.vehicle) && Objects.equals(jobStartedAt, that.jobStartedAt) && Objects.equals(jobPausedAt, that.jobPausedAt) && Objects.equals(jobEndedAt, that.jobEndedAt) && Objects.equals(managerNotes, that.managerNotes) && Objects.equals(employeeNotes, that.employeeNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, employee, serviceIds, vehicle, jobStartedAt, jobPausedAt, jobEndedAt, jobEnded, managerNotes, employeeNotes);
    }

    @Override
    public String toString() {
        return "JobCreateForm{" +
                "id=" + id +
                ", customer=" + customer +
                ", employee=" + employee +
                ", serviceIds=" + serviceIds +
                ", vehicle=" + vehicle +
                ", jobStartedAt=" + jobStartedAt +
                ", jobPausedAt=" + jobPausedAt +
                ", jobEndedAt=" + jobEndedAt +
                ", jobEnded=" + jobEnded +
                ", managerNotes='" + managerNotes + '\'' +
                ", employeeNotes='" + employeeNotes + '\'' +
                '}';
    }
}
