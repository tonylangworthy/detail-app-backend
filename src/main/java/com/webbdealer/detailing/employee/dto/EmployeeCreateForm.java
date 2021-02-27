package com.webbdealer.detailing.employee.dto;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeCreateForm implements Serializable {

    private Long id;

    private String firstName;

    private String middle;

    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeCreateForm that = (EmployeeCreateForm) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(middle, that.middle) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middle, lastName);
    }

    @Override
    public String toString() {
        return "EmployeeCreateForm{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middle='" + middle + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
