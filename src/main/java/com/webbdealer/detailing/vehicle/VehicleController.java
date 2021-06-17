package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dto.CustomerItemModel;
import com.webbdealer.detailing.security.JwtClaim;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import com.webbdealer.detailing.vehicle.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private VehicleService vehicleService;

    private VehicleLookupService vehicleLookupService;

    private VehicleItemModelAssembler vehicleItemModelAssembler;

    private PagedResourcesAssembler<VehicleResponse> pagedResourcesAssembler;

    @Autowired
    public VehicleController(
            VehicleService vehicleService,
            VehicleLookupService vehicleLookupService,
            VehicleItemModelAssembler vehicleItemModelAssembler,
            PagedResourcesAssembler<VehicleResponse> pagedResourcesAssembler) {
        this.vehicleService = vehicleService;
        this.vehicleLookupService = vehicleLookupService;
        this.vehicleItemModelAssembler = vehicleItemModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
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

    @GetMapping(path = "", produces = { "application/hal+json" })
    public PagedModel<VehicleItemModel> fetchVehicleList(Pageable pageable) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        Page<VehicleResponse> vehicleList = vehicleService.fetchAllVehicles(userDetails.getCompanyId(), pageable);


        return pagedResourcesAssembler.toModel(vehicleList, vehicleItemModelAssembler);
    }

    @PostMapping("")
    public ResponseEntity<?> storeVehicle(@RequestBody VehicleCreateForm vehicleForm) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();

        Vehicle vehicle = vehicleService.storeVehicleFromRequest(userDetails.getCompanyId(), vehicleForm);
        logger.info("Stored vehicle: " + vehicle.toString());
        return ResponseEntity.ok(vehicle);
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
