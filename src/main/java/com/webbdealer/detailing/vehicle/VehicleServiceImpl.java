package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.vehicle.dao.*;
import com.webbdealer.detailing.vehicle.dto.VehicleCreateForm;
import com.webbdealer.detailing.vehicle.dto.VehicleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private VehicleLookupService lookupService;
    
    private VehicleStorageService storageService;

    private VehicleRepository vehicleRepository;

    private CompanyService companyService;

    @Autowired
    public VehicleServiceImpl(VehicleLookupService lookupService, 
    		VehicleStorageService storageService, 
    		VehicleRepository vehicleRepository,
            CompanyService companyService) {
        this.lookupService = lookupService;
        this.storageService = storageService;
        this.vehicleRepository = vehicleRepository;
        this.companyService = companyService;
    }

    @Override
    public Vehicle fetchById(Long id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        return optionalVehicle.orElseThrow();
    }

    @Override
    public Page<VehicleResponse> fetchAllVehicles(Long companyId, Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAllByCompanyId(companyId, pageable);
        logger.info("vehicle total elements: " + vehicles.getTotalElements());
        List<Long> catalogIds = vehicles.stream().map(Vehicle::getCatalogId).collect(Collectors.toList());

        ResponseEntity<CatalogVehicleResponse[]> apiResponse = lookupService.lookupByCatalogIds(catalogIds);

        List<VehicleResponse> vehicleResponseList = convertApiResponseToVehicle(vehicles, apiResponse);

        return new PageImpl<>(vehicleResponseList, pageable, vehicles.getTotalElements());
    }

    @Override
    public Page<VehicleResponse> fetchVehiclesByCatalogIdList(Long companyId, List<Long> catalogIds, Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findByCompanyIdAndCatalogIdIn(companyId, catalogIds, pageable);

        ResponseEntity<CatalogVehicleResponse[]> apiResponse = lookupService.lookupByCatalogIds(catalogIds);
        List<VehicleResponse> vehicleResponseList = convertApiResponseToVehicle(vehicles, apiResponse);


        return new PageImpl<>(vehicleResponseList, pageable, vehicles.getTotalElements());
    }

    @Override
    public Vehicle fetchVehicleByVin(Long companyId, String vin) {

        Vehicle vehicle = null;

        // 1. Search database
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVinAndCompanyId(vin, companyId);

        // 2. If found, set response object
        if(optionalVehicle.isPresent()) {
            vehicle = optionalVehicle.get();
        }
        // 3. Otherwise, call API service.
        else {
            CatalogVehicleResponse apiResponse = lookupService.lookupByVin(vin);
            vehicle = storageService.storeVehicleFromApiResponse(apiResponse);
            logger.info(vehicle.toString());
        }
        return vehicle;
    }

    @Override
    public VehicleResponse fetchVehicleResponseByVin(Long companyId, String vin) {
    	
        Vehicle vehicle = fetchVehicleByVin(companyId, vin);

        VehicleResponse response = new VehicleResponse();
        response.setId(vehicle.getId());
        response.setVin(vehicle.getVin());
//        response.setYear(vehicle.getYear());
//        response.setMake(vehicle.getMake().getName());
//        response.setModel(vehicle.getModel().getName());
//        response.setTrim(vehicle.getTrim().getName());

        return response;
    }


    @Override
    public Vehicle fetchVehicleByYearMakeModel(String year, String make, String model) {
        return null;
    }

    @Override
    public Vehicle fetchOrCreateVehicleFromRequest(Long companyId, VehicleCreateForm vehicleCreateForm) {
        String vin = vehicleCreateForm.getVin();
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVinAndCompanyId(vin, companyId);
        return optionalVehicle.orElseGet(() -> storeVehicleFromRequest(companyId, vehicleCreateForm));
    }

    @Override
    public Vehicle storeVehicleFromRequest(Long companyId, VehicleCreateForm vehicleCreateForm) {

        logger.info(vehicleCreateForm.toString());

        Vehicle vehicle = new Vehicle();
        vehicle.setCatalogId(vehicleCreateForm.getCatalogId());
        vehicle.setVin(vehicleCreateForm.getVin());
        vehicle.setColor(vehicleCreateForm.getColor());

        LocalDate arrivalDate = vehicleCreateForm.getArrivalDate();
        LocalTime arrivalTime = vehicleCreateForm.getArrivalTime();
        LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);

        vehicle.setArrivalDate(arrivalDateTime);

        return storeVehicle(companyId, vehicle);
    }

    @Override
    public Vehicle storeVehicle(Long companyId, Vehicle vehicle) {
        Company company = companyService.attachVehicleToCompany(vehicle, companyId);
        vehicle.setCompany(company);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle attachVehicleToJob(Long vehicleId, Job job) {

        Vehicle vehicle = fetchById(vehicleId);
        job.setVehicle(vehicle);
        return vehicle;
    }

    @Override
    public VehicleResponse mapVehicleToResponse(Long companyId, Vehicle vehicle, Pageable pageable) {
        Page<VehicleResponse> vehiclePagedList = fetchVehiclesByCatalogIdList(companyId, Arrays.asList(vehicle.getCatalogId()), pageable);

        Optional<VehicleResponse> optionalVehicleResponse = Optional.empty();
        if(vehiclePagedList.getSize() == 1) {
            optionalVehicleResponse = Optional.of(vehiclePagedList.toList().get(0));
        }
        VehicleResponse vehicleResponse = optionalVehicleResponse.orElseThrow();
        vehicleResponse.setId(vehicle.getId());
        vehicleResponse.setVin(vehicle.getVin());
        vehicleResponse.setCreatedAt(vehicle.getCreatedAt());
        vehicleResponse.setUpdatedAt(vehicle.getUpdatedAt());
        return vehicleResponse;
    }

    private List<VehicleResponse> convertApiResponseToVehicle(
            Page<Vehicle> vehicles,
            ResponseEntity<CatalogVehicleResponse[]> apiResponse) {

        List<VehicleResponse> vehicleResponseList = new ArrayList<>();

        logger.info("status code: " + apiResponse.getStatusCodeValue());
        if(apiResponse.getStatusCodeValue() == 200) {
            CatalogVehicleResponse[] responseArray = apiResponse.getBody();
            logger.info("response array: " + responseArray.length);
            vehicles.forEach(vehicle -> {
                logger.info("catalogId: " + vehicle.getCatalogId());
                List<CatalogVehicleResponse> apiResponseList = Arrays.stream(responseArray)
                        .filter(response -> Long.valueOf(response.getVehicleId()).equals(vehicle.getCatalogId()))
                        .collect(Collectors.toList());
                logger.info("response list: " +apiResponseList.size() );
                apiResponseList.forEach(responseItem -> {
                    logger.info("New Vehicle Response");
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    vehicleResponse.setYear(responseItem.getYear());
                    vehicleResponse.setMake(responseItem.getMake());
                    vehicleResponse.setModel(responseItem.getModel());
                    vehicleResponse.setTrim(responseItem.getTrim());
                    vehicleResponse.setStyle(responseItem.getStyle());

                    vehicleResponse.setId(vehicle.getId());
                    vehicleResponse.setCatalogId(vehicle.getCatalogId());
                    vehicleResponse.setVin(vehicle.getVin());
                    vehicleResponse.setColor(vehicle.getColor());
                    vehicleResponse.setArrivedAt(vehicle.getArrivalDate());
                    vehicleResponse.setCreatedAt(vehicle.getCreatedAt());
                    vehicleResponse.setUpdatedAt(vehicle.getUpdatedAt());
                    vehicleResponseList.add(vehicleResponse);
                });
            });
        }
        return vehicleResponseList;
    }
}
