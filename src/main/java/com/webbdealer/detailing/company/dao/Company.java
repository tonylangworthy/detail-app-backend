package com.webbdealer.detailing.company.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.shared.BaseEntity;
import com.webbdealer.detailing.vehicle.dao.Vehicle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity implements Serializable {

    public Company() {}

    private Company(CompanyBuilder builder) {
        this.name = builder.name;
        this.address1 = builder.address1;
        this.address2 = builder.address2;
        this.city = builder.city;
        this.state = builder.state;
        this.zip = builder.zip;
        this.email = builder.email;
        this.phone = builder.phone;
        this.receiveTexts = builder.receiveTexts;
        this.website = builder.website;
        this.userEntities = builder.userEntities;
    }

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "receive_texts")
    private boolean receiveTexts;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<User> userEntities;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Customer> customers;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Recondition> reconditioningServices;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Job> jobs;

    public String getName() {
        return name;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getEmail() { return email; }

    public String getPhone() {
        return phone;
    }

    public boolean canReceiveTexts() {
        return receiveTexts;
    }

    public String getWebsite() {
        return website;
    }

    public List<User> getUsers() {
        return userEntities;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Recondition> getReconditioningServices() {
        return reconditioningServices;
    }

    public void setReconditioningServices(List<Recondition> reconditioningServices) {
        this.reconditioningServices = reconditioningServices;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Company company = (Company) o;
        return receiveTexts == company.receiveTexts && Objects.equals(name, company.name) && Objects.equals(address1, company.address1) && Objects.equals(address2, company.address2) && Objects.equals(city, company.city) && Objects.equals(state, company.state) && Objects.equals(zip, company.zip) && Objects.equals(email, company.email) && Objects.equals(phone, company.phone) && Objects.equals(website, company.website) && Objects.equals(userEntities, company.userEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, address1, address2, city, state, zip, email, phone, receiveTexts, website, userEntities);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", receiveTexts=" + receiveTexts +
                ", website='" + website + '\'' +
                ", userEntities=" + userEntities +
                '}';
    }

    public static class CompanyBuilder {

        private String name;
        private String address1;
        private String address2;
        private String city;
        private String state;
        private String zip;
        private String email;
        private String phone;
        private boolean receiveTexts;
        private String website;
        private List<User> userEntities = new ArrayList<>();

        public CompanyBuilder(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        public CompanyBuilder address1(String address1) {
            this.address1 = address1;
            return this;
        }

        public CompanyBuilder address2(String address2) {
            this.address2 = address2;
            return this;
        }

        public CompanyBuilder city(String city) {
            this.city = city;
            return this;
        }

        public CompanyBuilder state(String state) {
            this.state = state;
            return this;
        }

        public CompanyBuilder zip(String zip) {
            this.zip = zip;
            return this;
        }

        public CompanyBuilder canReceiveTexts(boolean canReceiveTexts) {
            this.receiveTexts = canReceiveTexts;
            return this;
        }

        public CompanyBuilder website(String website) {
            this.website = website;
            return this;
        }

        public CompanyBuilder addUser(User user) {
            this.userEntities.add(user);
            return this;
        }

        public Company build() {
            return new Company(this);
        }
    }
}
