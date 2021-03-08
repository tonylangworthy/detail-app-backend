package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.security.JwtClaim;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import com.webbdealer.detailing.vehicle.dto.VehicleCreateForm;
import com.webbdealer.detailing.vehicle.dto.VinFormRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;

import javax.validation.Valid;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private VehicleService vehicleService;

    private VehicleLookupService vehicleLookupService;

    @Autowired
    public VehicleController(VehicleService vehicleService, VehicleLookupService vehicleLookupService) {
        this.vehicleService = vehicleService;
        this.vehicleLookupService = vehicleLookupService;
    }

//    @GetMapping("/vin")
//    public ResponseEntity<?> findVehicleByVin(@Valid @RequestBody VinFormRequest vinForm, BindingResult bindingResult) {
//
//    	SecurityContext context = SecurityContextHolder.getContext();
//        Authentication auth = context.getAuthentication();
//
//        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();
//
//        String vin = vinForm.getVin();
//        if(bindingResult.hasErrors()) {
//            logger.info(vin);
//            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
//        }
//        return ResponseEntity.ok(vehicleService.fetchVehicleResponseByVin(userDetails.getCompanyId(), vin));
//    }

    @GetMapping("")
    public ResponseEntity<?> fetchVehicleList() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();


        return ResponseEntity.ok(vehicleService.fetchAllVehicles(userDetails.getCompanyId()));
    }

    @PostMapping("")
    public ResponseEntity<?> createVehicle(@RequestBody VehicleCreateForm vehicleForm) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        Vehicle vehicle = vehicleService.storeVehicleFromRequest(userDetails.getCompanyId(), vehicleForm);
        return ResponseEntity.ok(vehicle.toString());
    }

    @GetMapping("/catalog/vin/{vin}")
    public ResponseEntity<?> findVehicleByVin(@Valid @PathVariable String vin) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();


//        if(bindingResult.hasErrors()) {
//            logger.info(vin);
//            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
//        }
        return ResponseEntity.ok(vehicleLookupService.lookupByVin(vin));
//        return ResponseEntity.ok("VIN: " + vin.toString());
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<?> findVehicleByCatalogId(@PathVariable Long id) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        System.out.println("Path Variable: " + id);


        return vehicleLookupService.lookupByCatalogIds(Arrays.asList(id));
    }
}
