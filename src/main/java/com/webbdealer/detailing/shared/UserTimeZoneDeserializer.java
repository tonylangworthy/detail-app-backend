package com.webbdealer.detailing.shared;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.TextNode;
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

public class UserTimeZoneDeserializer extends JsonDeserializer<ZonedDateTime> {

    private CompanyRepository companyRepository;

    @Autowired
    public UserTimeZoneDeserializer(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        if(p.getText().equals("")) {
            return null;
        }
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();
        Optional<Company> optionalCompany = companyRepository.findById(userDetails.getCompanyId());
        Company company = optionalCompany.orElseThrow();
        String companyTimezone = company.getTimezone();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(p.getText(), formatter);

//        ZonedDateTime zonedDateTime = ZonedDateTime.parse(p.getText(), formatter).withZoneSameLocal(ZoneId.of(companyTimezone));
        System.out.println("local: " + localDateTime);

        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(companyTimezone));


        System.out.println("zoned to utc: " + zonedDateTime);



        System.out.println(p.getText());

//        System.out.println(userDetails.toString());

        return zonedDateTime;
    }
}
