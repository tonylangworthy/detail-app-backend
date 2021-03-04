package com.webbdealer.detailing.timeclock;

import com.webbdealer.detailing.security.JwtClaim;
import com.webbdealer.detailing.timeclock.dto.TimeClockRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/time-clock")
public class TimeClockController {

    private static final Logger logger = LoggerFactory.getLogger(TimeClockController.class);

    private TimeClockService timeClockService;

    @Autowired
    public TimeClockController(TimeClockService timeClockService) {
        this.timeClockService = timeClockService;
    }

    @PostMapping("")
    public ResponseEntity<?> punchTimeClock(@RequestBody TimeClockRequest timeClockRequest) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        timeClockService.punchTimeClock(userDetails.getUserId(), timeClockRequest);

        return ResponseEntity.ok("Time clock punched!");
    }
}