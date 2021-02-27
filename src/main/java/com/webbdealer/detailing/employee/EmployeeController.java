package com.webbdealer.detailing.employee;

import com.webbdealer.detailing.employee.dto.EmployeePinForm;
import com.webbdealer.detailing.security.JwtClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        return ResponseEntity.ok("hello user!");
    }

    @GetMapping("")
    public ResponseEntity<?> employees() {
        return ResponseEntity.ok(employeeService.fetchEmployees());
    }

    @PostMapping("/pin")
    public ResponseEntity<?> updatePin(@RequestBody EmployeePinForm pinForm) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        return ResponseEntity.ok(employeeService.storePin(pinForm, userDetails.getUserId()));
    }
}
