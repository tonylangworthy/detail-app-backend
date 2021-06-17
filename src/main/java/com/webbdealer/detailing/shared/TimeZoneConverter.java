package com.webbdealer.detailing.shared;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeZoneConverter {

    public static ZonedDateTime companyDateTimeNow(ZoneId companyZoneId) {
        ZonedDateTime utcZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        return utcZonedDateTime.withZoneSameInstant(companyZoneId);
    }

    public static ZonedDateTime stringDateTimeToUtcZone(String dateTimeString, ZoneId companyZoneId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);

        ZonedDateTime companyZonedDateTime = ZonedDateTime.of(localDateTime, companyZoneId);
        System.out.println("zoned to company: " + companyZonedDateTime);

        // convert company time zone to UTC
        return companyZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
    }
}
