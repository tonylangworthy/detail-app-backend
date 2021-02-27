package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.vehicle.dao.*;

public interface VehicleStorageService {

	Vehicle storeVehicleFromApiResponse(CatalogApiResponse apiResponse);

	Make findOrCreateMake(String makeName);

	Model findOrCreateModel(String modelName);

	Trim findOrCreateTrim(String trimName);
}
