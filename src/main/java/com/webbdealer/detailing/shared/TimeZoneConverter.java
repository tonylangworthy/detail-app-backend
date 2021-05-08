package com.webbdealer.detailing.shared;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneConverter {

    public static ZonedDateTime companyDateTimeNow(ZoneId companyZoneId) {
        ZonedDateTime utcZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        return utcZonedDateTime.withZoneSameInstant(companyZoneId);
    }
}
