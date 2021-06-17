package com.webbdealer.detailing.shared;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.TextNode;
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
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.Optional;

public class UserTimeZoneDeserializer extends JsonDeserializer<LocalDateTime> {

    private CompanyService companyService;

    @Autowired
    public UserTimeZoneDeserializer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        if(p.getText().equals("")) {
            return null;
        }
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        ZoneId companyZoneId = companyService.companyTimeZone(userDetails.getCompanyId());
        ZonedDateTime utcZonedDateTime = TimeZoneConverter.stringDateTimeToUtcZone(p.getText(), companyZoneId);

        System.out.println("zoned to utc: " + utcZonedDateTime);

        return utcZonedDateTime.toLocalDateTime();
    }
}
