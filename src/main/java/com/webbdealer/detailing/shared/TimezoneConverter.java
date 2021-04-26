package com.webbdealer.detailing.shared;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimezoneConverter {

    private String zoneId;

    private TimezoneConverter(TimezoneConverterBuilder builder) {
        this.zoneId = builder.zoneId;
    }

    public LocalDateTime fromUtcToLocalDateTime(LocalDateTime utcDateTime) {
//        return utcDateTime.withZoneSameInstant(ZoneId.of(zoneId)).toLocalDateTime();
        return null;
    }

    public LocalDateTime fromLocalDateTimeToUtc(LocalDateTime localDateTime) {
//        return localDateTime.atZone(ZoneId.of(zoneId)).withZoneSameInstant(ZoneId.of("UTC"));
        return null;
    }


    public static class TimezoneConverterBuilder {

        private String zoneId;

        private LocalDateTime zonedDateTime;

        private LocalDateTime localDateTime;

        public TimezoneConverterBuilder(String zoneId) {
            this.zoneId = zoneId;
        }

        public TimezoneConverter build() {
            return new TimezoneConverter(this);
        }
    }
}
