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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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

        timeClockService.punchTimeClock(userDetails().getUserId(), timeClockRequest);

        return ResponseEntity.ok("Time clock punched!");
    }


    @GetMapping("/clocked-in")
    public ResponseEntity<?> showClockedInEmployees() {
        return ResponseEntity.ok(timeClockService.listClockedInEmployees(userDetails().getCompanyId()));
//        return ResponseEntity.ok(ZonedDateTime.now());
//        return ResponseEntity.ok(LocalDateTime.now());
    }

    private JwtClaim userDetails() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        return (JwtClaim) auth.getPrincipal();
    }
}
