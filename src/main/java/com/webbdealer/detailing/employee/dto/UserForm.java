package com.webbdealer.detailing.employee.dto;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class UserForm implements Serializable {

    private String firstName;

    private String middle;

    private String lastName;

    private String phone;

    private boolean isMobile;

    private String email;

    private String password;

    private String passwordConfirm;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
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

    public boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(boolean isMobile) {
        this.isMobile = isMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserForm userForm = (UserForm) o;
        return Objects.equals(firstName, userForm.firstName) && Objects.equals(middle, userForm.middle) && Objects.equals(lastName, userForm.lastName) && Objects.equals(phone, userForm.phone) && Objects.equals(isMobile, userForm.isMobile) && Objects.equals(email, userForm.email) && Objects.equals(password, userForm.password) && Objects.equals(passwordConfirm, userForm.passwordConfirm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middle, lastName, phone, isMobile, email, password, passwordConfirm);
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "firstName='" + firstName + '\'' +
                ", middle='" + middle + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", isMobile='" + isMobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
