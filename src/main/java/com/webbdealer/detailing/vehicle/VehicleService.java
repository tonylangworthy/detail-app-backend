package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import com.webbdealer.detailing.vehicle.dto.VehicleCreateForm;
import com.webbdealer.detailing.vehicle.dto.VehicleResponse;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    Optional<Vehicle> fetchById(Long id);

    Vehicle fetchByIdReference(Long id);

    List<VehicleResponse> fetchAllVehicles(Long companyId);

    List<VehicleResponse> fetchVehiclesByCatalogIdList(Long companyId, List<Long> catalogIds);

    Vehicle fetchVehicleByVin(Long companyId, String vin);

    VehicleResponse fetchVehicleResponseByVin(Long companyId, String vin);

    Vehicle fetchVehicleByYearMakeModel(String year, String make, String model);

    Vehicle fetchOrCreateVehicleFromRequest(Long companyId, VehicleCreateForm vehicleCreateForm);

    Vehicle storeVehicleFromRequest(Long companyId, VehicleCreateForm vehicleCreateForm);

    Vehicle storeVehicle(Long companyId, Vehicle vehicle);

    Vehicle attachVehicleToJob(Long vehicleId, Job job);

    VehicleResponse mapVehicleToResponse(Long companyId, Vehicle vehicle);
}
