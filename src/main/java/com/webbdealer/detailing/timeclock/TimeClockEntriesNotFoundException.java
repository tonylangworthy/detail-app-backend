package com.webbdealer.detailing.timeclock;

public class TimeClockEntriesNotFoundException extends RuntimeException {

    public TimeClockEntriesNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public TimeClockEntriesNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
