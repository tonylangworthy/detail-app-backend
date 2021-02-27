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

		Make make = findOrCreateMake(apiResponse.getMake());
		vehicle.setMake(make);

		Model model = findOrCreateModel(apiResponse.getModel());
		vehicle.setModel(model);

		return vehicleRepository.save(vehicle);
	}

	@Override
	public Make findOrCreateMake(String makeName) {
		return null;
	}

	@Override
	public Model findOrCreateModel(String modelName) {
		return null;
	}

	@Override
	public Trim findOrCreateTrim(String trimName) {
		return null;
	}
}
