package com.webbdealer.detailing.registration.dto;

import com.webbdealer.detailing.company.dto.CompanyForm;
import com.webbdealer.detailing.employee.dto.UserForm;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class RegistrationForm implements Serializable {

    private CompanyForm company;

    private UserForm user;

    public CompanyForm getCompany() {
        return company;
    }

    public void setCompany(CompanyForm company) {
        this.company = company;
    }

    public UserForm getUser() {
        return user;
    }

    public void setUser(UserForm user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationForm that = (RegistrationForm) o;
        return Objects.equals(company, that.company) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, user);
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "company=" + company +
                ", user=" + user +
                '}';
    }
}
