package com.webbdealer.detailing.recondition;

import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.recondition.dto.ReconditionCreateForm;
import com.webbdealer.detailing.recondition.dto.ReconditionWrapper;
import com.webbdealer.detailing.security.JwtClaim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ReconditionController {

    private ReconditionService reconditionService;

    @Autowired
    public ReconditionController(ReconditionService reconditionService) {
        this.reconditionService = reconditionService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('WRITE_JOBS')")
//    @RolesAllowed("ROLE_DETAILER")
    public ResponseEntity<?> listAllReconditioningServices() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        System.out.println(userDetails.toString());
        System.out.println(auth.toString());
        return ResponseEntity.ok(reconditionService.fetchServices(userDetails.getCompanyId()));
    }

    @PostMapping("")
    public ResponseEntity<?> createReconditioningServices(@RequestBody ReconditionWrapper servicesList) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        System.out.println(servicesList.toString());
        List<Recondition> reconList = reconditionService.storeReconditionServiceFromRequest(userDetails.getCompanyId(), servicesList);

        return ResponseEntity.ok(reconList.toString());
    }
}
