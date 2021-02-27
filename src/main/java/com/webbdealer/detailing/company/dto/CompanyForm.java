package com.webbdealer.detailing.company.dto;

import java.io.Serializable;
import java.util.Objects;

public class CompanyForm implements Serializable {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyForm that = (CompanyForm) o;
        return receiveTexts == that.receiveTexts && Objects.equals(name, that.name) && Objects.equals(address1, that.address1) && Objects.equals(address2, that.address2) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(zip, that.zip) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address1, address2, city, state, zip, email, phone, receiveTexts, website);
    }

    @Override
    public String toString() {
        return "CompanyForm{" +
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
                '}';
    }
}
