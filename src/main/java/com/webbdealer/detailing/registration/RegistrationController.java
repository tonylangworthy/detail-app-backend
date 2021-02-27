package com.webbdealer.detailing.registration;

import com.webbdealer.detailing.registration.dto.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("")
    public ResponseEntity<RegistrationForm> register(@RequestBody RegistrationForm registrationForm) {
        return ResponseEntity.ok(registrationService.register(registrationForm));

    }
}
