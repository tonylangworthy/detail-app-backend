package com.webbdealer.detailing.company;

public class CompanyExistsException extends RuntimeException {

    public CompanyExistsException(String message) {
        super(message);
    }

    public CompanyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
