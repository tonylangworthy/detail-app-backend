package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import com.webbdealer.detailing.vehicle.dto.VehicleCreateForm;
import com.webbdealer.detailing.vehicle.dto.VehicleResponse;

public interface VehicleService {

    Vehicle findVehicleByVin(Long companyId, String vin);

    VehicleResponse findVehicleResponseByVin(Long companyId, String vin);

    Vehicle findByYearMakeModel(String year, String make, String model);

    Vehicle storeVehicleFromRequest(Long companyId, VehicleCreateForm vehicleCreateForm);

    Vehicle storeVehicle(Long companyId, Vehicle vehicle);

    Vehicle attachVehicleToJob(Long vehicleId, Job job);

    VehicleResponse mapVehicleToResponse(Vehicle vehicle);
}
