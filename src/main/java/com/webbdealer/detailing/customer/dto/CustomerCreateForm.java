package com.webbdealer.detailing.customer.dto;

import com.webbdealer.detailing.customer.dao.CustomerType;

import java.io.Serializable;
import java.util.Objects;

public class CustomerCreateForm implements Serializable {

    private Long id;

    private CustomerType customerType;

    private String business;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private boolean receiveTexts;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCreateForm that = (CustomerCreateForm) o;
        return receiveTexts == that.receiveTexts && Objects.equals(id, that.id) && Objects.equals(customerType, that.customerType) && Objects.equals(business, that.business) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerType, business, firstName, lastName, email, phone, receiveTexts, notes);
    }

    @Override
    public String toString() {
        return "CustomerCreateForm{" +
                "id=" + id +
                ", customerType='" + customerType + '\'' +
                ", business='" + business + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", receiveTexts=" + receiveTexts +
                ", notes='" + notes + '\'' +
                '}';
    }
}
