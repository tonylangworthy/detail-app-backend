package com.webbdealer.detailing.job.dto;

import com.webbdealer.detailing.customer.dao.CustomerType;
import com.webbdealer.detailing.job.dao.JobStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobItemResponse implements Serializable {

    private Long id;

    private JobStatus status;

    private CustomerType customerType;

    private String customerFirstName;

    private String customerLastName;

    private String customerBusiness;

    private String vehicleYear;

    private String vehicleMake;

    private String vehicleModel;

    private String vehicleTrim;

    private List<String> assignedEmployees = new ArrayList<>();

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

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerBusiness() {
        return customerBusiness;
    }

    public void setCustomerBusiness(String customerBusiness) {
        this.customerBusiness = customerBusiness;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleTrim() {
        return vehicleTrim;
    }

    public void setVehicleTrim(String vehicleTrim) {
        this.vehicleTrim = vehicleTrim;
    }

    public List<String> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(List<String> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobItemResponse that = (JobItemResponse) o;
        return Objects.equals(id, that.id) && status == that.status && customerType == that.customerType && Objects.equals(customerFirstName, that.customerFirstName) && Objects.equals(customerLastName, that.customerLastName) && Objects.equals(customerBusiness, that.customerBusiness) && Objects.equals(vehicleYear, that.vehicleYear) && Objects.equals(vehicleMake, that.vehicleMake) && Objects.equals(vehicleModel, that.vehicleModel) && Objects.equals(vehicleTrim, that.vehicleTrim) && Objects.equals(assignedEmployees, that.assignedEmployees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, customerType, customerFirstName, customerLastName, customerBusiness, vehicleYear, vehicleMake, vehicleModel, vehicleTrim, assignedEmployees);
    }

    @Override
    public String toString() {
        return "JobItemResponse{" +
                "id=" + id +
                ", status=" + status +
                ", customerType=" + customerType +
                ", customerFirstName='" + customerFirstName + '\'' +
                ", customerLastName='" + customerLastName + '\'' +
                ", customerBusiness='" + customerBusiness + '\'' +
                ", vehicleYear='" + vehicleYear + '\'' +
                ", vehicleMake='" + vehicleMake + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleTrim='" + vehicleTrim + '\'' +
                ", assignedEmployees=" + assignedEmployees +
                '}';
    }
}
