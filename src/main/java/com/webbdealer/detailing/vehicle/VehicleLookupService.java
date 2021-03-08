package com.webbdealer.detailing.vehicle;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VehicleLookupService {

    ResponseEntity<CatalogVehicleResponse[]> lookupByCatalogIds(List<Long> idList);

    CatalogVehicleResponse lookupByVin(String vin);

    CatalogVehicleResponse lookupByYearMakeModel(String year, String make, String model);

    CatalogVehicleResponse lookupByYearMakeModelTrim(String year, String make, String model, String trim);

    String commaSeparatedIdList(List<Long> idList);
}
