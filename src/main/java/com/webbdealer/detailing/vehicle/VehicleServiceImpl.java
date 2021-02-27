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
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private VehicleLookupService lookupService;
    
    private VehicleStorageService storageService;

    private VehicleRepository vehicleRepository;

    private MakeRepository makeRepository;

    private ModelRepository modelRepository;

    private TrimRepository trimRepository;

    private CompanyService companyService;

    @Autowired
    public VehicleServiceImpl(VehicleLookupService lookupService, 
    		VehicleStorageService storageService, 
    		VehicleRepository vehicleRepository,
            MakeRepository makeRepository,
            ModelRepository modelRepository,
            TrimRepository trimRepository,
            CompanyService companyService) {
        this.lookupService = lookupService;
        this.storageService = storageService;
        this.vehicleRepository = vehicleRepository;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.trimRepository = trimRepository;
        this.companyService = companyService;
    }

    @Override
    public Vehicle findVehicleByVin(Long companyId, String vin) {

        Vehicle vehicle = null;

        // 1. Search database
        Optional<Vehicle> optionalVehicle = vehicleRepository.findByVinAndCompanyId(vin, companyId);

        // 2. If found, set response object
        if(optionalVehicle.isPresent()) {
            vehicle = optionalVehicle.get();
        }
        // 3. Otherwise, call API service.
        else {
            CatalogApiResponse apiResponse = lookupService.lookupByVin(vin);
            vehicle = storageService.storeVehicleFromApiResponse(apiResponse);
            logger.info(vehicle.toString());
        }
        return vehicle;
    }

    @Override
    public VehicleResponse findVehicleResponseByVin(Long companyId, String vin) {
    	
        Vehicle vehicle = findVehicleByVin(companyId, vin);

        VehicleResponse response = new VehicleResponse();
        response.setId(vehicle.getId());
        response.setVin(vehicle.getVin());
        response.setYear(vehicle.getYear());
        response.setMake(vehicle.getMake().getName());
        response.setModel(vehicle.getModel().getName());
        response.setTrim(vehicle.getTrim().getName());

        return response;
    }


    @Override
    public Vehicle findByYearMakeModel(String year, String make, String model) {
        return null;
    }

    @Override
    public Vehicle storeVehicleFromRequest(Long companyId, VehicleCreateForm vehicleCreateForm) {

        logger.info(vehicleCreateForm.toString());

        Optional<Make> optionalMake = makeRepository.findByNameIgnoreCase(vehicleCreateForm.getMake());
        Make make = optionalMake.orElseGet(() -> makeRepository.save(new Make(vehicleCreateForm.getMake())));

        Optional<Model> optionalModel = modelRepository.findByNameIgnoreCase(vehicleCreateForm.getModel());
        Model model = optionalModel.orElseGet(() -> modelRepository.save(new Model(vehicleCreateForm.getModel())));

        Optional<Trim> optionalTrim = trimRepository.findByNameIgnoreCase(vehicleCreateForm.getTrim());
        Trim trim = optionalTrim.orElseGet(() -> trimRepository.save(new Trim(vehicleCreateForm.getTrim())));

        Vehicle vehicle = new Vehicle();
        vehicle.setVin(vehicleCreateForm.getVin());
        vehicle.setYear(vehicleCreateForm.getYear());
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setTrim(trim);

        String arrivalDate = vehicleCreateForm.getArrivalDate();
        String arrivalTime = vehicleCreateForm.getArrivalTime();

        vehicle.setArrivalDate(LocalDateTime.now());

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

        Vehicle vehicle = vehicleRepository.getOne(vehicleId);
        job.setVehicle(vehicle);
        return vehicle;
    }

    @Override
    public VehicleResponse mapVehicleToResponse(Vehicle vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setId(vehicle.getId());
        vehicleResponse.setVin(vehicle.getVin());
        vehicleResponse.setYear(vehicle.getYear());
        vehicleResponse.setMake(vehicle.getMake().getName());
        vehicleResponse.setModel(vehicle.getModel().getName());
        vehicleResponse.setTrim(vehicle.getTrim().getName());
        vehicleResponse.setCreatedAt(vehicle.getCreatedAt().toString());
//        vehicleResponse.setUpdatedAt(vehicle.getUpdatedAt().toString());
        return vehicleResponse;
    }
}
