package com.webbdealer.detailing.customer.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.shared.BaseEntity;
import com.webbdealer.detailing.vehicle.dao.Vehicle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity implements Serializable {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "receive_texts")
    private boolean receiveTexts;

    @Column(name = "email")
    private String email;

    @Column(name = "business")
    private String business;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    private Company company;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isReceiveTexts() {
        return receiveTexts;
    }

    public void setReceiveTexts(boolean receiveTexts) {
        this.receiveTexts = receiveTexts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return receiveTexts == customer.receiveTexts && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(phone, customer.phone) && Objects.equals(email, customer.email) && Objects.equals(business, customer.business) && Objects.equals(notes, customer.notes) && customerType == customer.customerType && Objects.equals(company, customer.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, phone, receiveTexts, email, business, notes, customerType, company);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", receiveTexts=" + receiveTexts +
                ", email='" + email + '\'' +
                ", business='" + business + '\'' +
                ", notes='" + notes + '\'' +
                ", customerType=" + customerType +
                ", company=" + company +
                '}';
    }
}
