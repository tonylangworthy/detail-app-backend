package com.webbdealer.detailing.employee.dto;

import java.io.Serializable;
import java.util.Objects;

public class EmployeePinForm implements Serializable {

    private String pin;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeePinForm)) return false;
        EmployeePinForm that = (EmployeePinForm) o;
        return Objects.equals(pin, that.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin);
    }

    @Override
    public String toString() {
        return "EmployeePinForm{" +
                "pin='" + pin + '\'' +
                '}';
    }
}
