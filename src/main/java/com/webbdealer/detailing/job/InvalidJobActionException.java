package com.webbdealer.detailing.job;

public class InvalidJobActionException extends RuntimeException {

    public InvalidJobActionException(String message) {
        super(message);
    }

    public InvalidJobActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
