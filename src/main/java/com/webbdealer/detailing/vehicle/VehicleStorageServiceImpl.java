package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.vehicle.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleStorageServiceImpl implements VehicleStorageService {

	private VehicleRepository vehicleRepository;

	@Autowired
	public VehicleStorageServiceImpl(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public Vehicle storeVehicleFromApiResponse(CatalogApiResponse apiResponse) {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setVin(apiResponse.getVin());
		vehicle.setYear(apiResponse.getYear());

		return vehicleRepository.save(vehicle);
	}
}
