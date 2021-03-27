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

//    @Column(name = "job_started_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    private ZonedDateTime jobStartedAt;
//
//    @Column(name = "job_paused_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    private ZonedDateTime jobPausedAt;
//
//    @Column(name = "job_ended_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    private ZonedDateTime jobEndedAt;
//
//    @Column(name = "job_canceled")
//    private boolean jobCanceled;

    @Column(name = "job_status")
    private JobStatus jobStatus;

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

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "employees_jobs",
//            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id")
//    )
//    private List<User> employees = new ArrayList<>();

    @OneToMany(mappedBy = "job")
    private List<JobAction> jobActions;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reconservices_jobs",
            joinColumns = @JoinColumn(name = "reconservices_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"))
    private List<Recondition> reconditioningServices = new ArrayList<>();

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
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

    public List<JobAction> getJobActions() {
        return jobActions;
    }

    public void setJobActions(List<JobAction> jobActions) {
        this.jobActions = jobActions;
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
        return jobStatus == job.jobStatus && Objects.equals(managerNotes, job.managerNotes) && Objects.equals(employeeNotes, job.employeeNotes) && Objects.equals(company, job.company) && Objects.equals(customer, job.customer) && Objects.equals(vehicle, job.vehicle) && Objects.equals(jobActions, job.jobActions) && Objects.equals(reconditioningServices, job.reconditioningServices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), jobStatus, managerNotes, employeeNotes, company, customer, vehicle, jobActions, reconditioningServices);
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobStatus=" + jobStatus +
                ", managerNotes='" + managerNotes + '\'' +
                ", employeeNotes='" + employeeNotes + '\'' +
                ", company=" + company +
                ", customer=" + customer +
                ", vehicle=" + vehicle +
                '}';
    }
}
