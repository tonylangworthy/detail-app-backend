package com.webbdealer.detailing.customer.dto;

import com.webbdealer.detailing.customer.dao.CustomerType;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.Objects;

@Relation(collectionRelation = "customer")
public class CustomerModel extends RepresentationModel<CustomerModel> {

    private Long id;

    private CustomerType customerType;

    private String firstName;

    private String lastName;

    private String business;

    private String email;

    private String phone;

    private boolean receiveTexts;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerModel that = (CustomerModel) o;
        return receiveTexts == that.receiveTexts && Objects.equals(id, that.id) && customerType == that.customerType && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(business, that.business) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(notes, that.notes) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerType, firstName, lastName, business, email, phone, receiveTexts, notes, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", customerType=" + customerType +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", business='" + business + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", receiveTexts=" + receiveTexts +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
