package com.webbdealer.detailing.job;

public class InvalidJobStatusException extends RuntimeException {

    public InvalidJobStatusException(String message) {
        super(message);
    }

    public InvalidJobStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
