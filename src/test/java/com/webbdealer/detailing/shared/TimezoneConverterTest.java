package com.webbdealer.detailing.shared;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TimezoneConverterTest {

    @Test
    public void fromUtcToLocalDateTime_Test() {
        TimezoneConverter timezoneConverter = new TimezoneConverter.TimezoneConverterBuilder("America/Chicago").build();

        ZonedDateTime inputDateTime = ZonedDateTime.parse("2021-03-13T22:04:00+00");
        LocalDateTime outputDateTime = timezoneConverter.fromUtcToLocalDateTime(inputDateTime);

        assertEquals("2021-03-13T16:04", outputDateTime.toString());
    }

    @Test
    public void fromLocalDateTimeToUtc_Test() {
        TimezoneConverter timezoneConverter = new TimezoneConverter.TimezoneConverterBuilder("America/Chicago").build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

        LocalDateTime inputDateTime = LocalDateTime.parse("03/13/2021 04:04:00 PM", formatter);
        ZonedDateTime outputDateTime = timezoneConverter.fromLocalDateTimeToUtc(inputDateTime);

        assertEquals("2021-03-13T22:04", outputDateTime.toLocalDateTime().toString());
    }

}
