package com.webbdealer.detailing.job.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.shared.BaseEntity;
import com.webbdealer.detailing.vehicle.dao.Vehicle;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "jobs")
public class Job extends BaseEntity implements Serializable {

    @Column(name = "job_started_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime jobStartedAt;

    @Column(name = "job_paused_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime jobPausedAt;

    @Column(name = "job_ended_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime jobEndedAt;

    @Column(name = "job_canceled")
    private boolean jobCanceled;

    @Column(name = "manager_notes", columnDefinition = "TEXT")
    private String managerNotes;

    @Column(name = "employee_notes", columnDefinition = "TEXT")
    private String employeeNotes;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonBackReference
    private Vehicle vehicle;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employees_jobs",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id")
    )
    private List<User> employees = new ArrayList<>();

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reconservices_jobs",
            joinColumns = @JoinColumn(name = "reconservices_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"))
    private List<Recondition> reconditioningServices = new ArrayList<>();

    public ZonedDateTime getJobStartedAt() {
        return jobStartedAt;
    }

    public void setJobStartedAt(ZonedDateTime jobStartedAt) {
        this.jobStartedAt = jobStartedAt;
    }

    public ZonedDateTime getJobPausedAt() {
        return jobPausedAt;
    }

    public void setJobPausedAt(ZonedDateTime jobPausedAt) {
        this.jobPausedAt = jobPausedAt;
    }

    public ZonedDateTime getJobEndedAt() {
        return jobEndedAt;
    }

    public void setJobEndedAt(ZonedDateTime jobEndedAt) {
        this.jobEndedAt = jobEndedAt;
    }

    public boolean isJobCanceled() {
        return jobCanceled;
    }

    public void setJobCanceled(boolean jobCanceled) {
        this.jobCanceled = jobCanceled;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Recondition> getReconditioningServices() {
        return reconditioningServices;
    }

    public void setReconditioningServices(List<Recondition> reconditioningServices) {
        this.reconditioningServices = reconditioningServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Job job = (Job) o;
        return jobCanceled == job.jobCanceled && Objects.equals(jobStartedAt, job.jobStartedAt) && Objects.equals(jobPausedAt, job.jobPausedAt) && Objects.equals(jobEndedAt, job.jobEndedAt) && Objects.equals(managerNotes, job.managerNotes) && Objects.equals(employeeNotes, job.employeeNotes) && Objects.equals(company, job.company) && Objects.equals(customer, job.customer) && Objects.equals(vehicle, job.vehicle) && Objects.equals(employees, job.employees) && Objects.equals(reconditioningServices, job.reconditioningServices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobStartedAt, jobPausedAt, jobEndedAt, jobCanceled, managerNotes, employeeNotes, company, customer, vehicle, employees, reconditioningServices);
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobStartedAt=" + jobStartedAt +
                ", jobPausedAt=" + jobPausedAt +
                ", jobEndedAt=" + jobEndedAt +
                ", jobFinished=" + jobCanceled +
                ", managerNotes='" + managerNotes + '\'' +
                ", employeeNotes='" + employeeNotes + '\'' +
                ", company=" + company +
                ", customer=" + customer +
                ", vehicle=" + vehicle +
                ", reconditioningServices=" + reconditioningServices +
                '}';
    }
}
