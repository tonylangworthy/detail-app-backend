package com.webbdealer.detailing.shared;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.company.dao.CompanyRepository;
import com.webbdealer.detailing.security.CustomUserDetails;
import com.webbdealer.detailing.security.JwtClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

public class UserTimeZoneSerializer extends JsonSerializer<LocalDateTime> {

    private CompanyService companyService;

    @Autowired
    public UserTimeZoneSerializer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.ENGLISH);

        // First, create a ZonedDateTime in UTC
//        ZonedDateTime utcZonedDateTime = ZonedDateTime.of(value, ZoneId.of("UTC"));

        // Convert from UTC to whatever time zone the company is using
        ZoneId companyTimezone = companyService.companyTimeZone(userDetails.getCompanyId());
        ZonedDateTime companyZonedDateTime = TimeZoneConverter.companyDateTimeNow(companyTimezone);

        gen.writeString(companyZonedDateTime.format(formatter));

    }
}
