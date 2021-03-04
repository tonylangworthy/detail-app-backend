package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.vehicle.dao.*;

public interface VehicleStorageService {

	Vehicle storeVehicleFromApiResponse(CatalogApiResponse apiResponse);
}
