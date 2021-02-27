package com.webbdealer.detailing.vehicle;

public interface VehicleLookupService {

    CatalogApiResponse lookupByVin(String vin);

    CatalogApiResponse lookupByYearMakeModel(String year, String make, String model);

    CatalogApiResponse lookupByYearMakeModelTrim(String year, String make, String model, String trim);

    CatalogApiResponse lookupByApiId(String id);
}
