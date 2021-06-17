package com.webbdealer.detailing.customer.dto;

import com.webbdealer.detailing.customer.dao.CustomerType;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

@Relation(collectionRelation = "customers")
public class CustomerItemModel extends RepresentationModel<CustomerItemModel> {

    private Long id;

    private CustomerType customerType;

    private String fullName;

    private String business;

    private String phone;

    public CustomerItemModel() {
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerItemModel that = (CustomerItemModel) o;
        return Objects.equals(id, that.id) && customerType == that.customerType && Objects.equals(fullName, that.fullName) && Objects.equals(business, that.business) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, customerType, fullName, business, phone);
    }

    @Override
    public String toString() {
        return "CustomerItemModel{" +
                "id=" + id +
                ", customerType=" + customerType +
                ", fullName='" + fullName + '\'' +
                ", business='" + business + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
