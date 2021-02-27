package com.webbdealer.detailing.vehicle;

import com.webbdealer.detailing.vehicle.dto.VinFormRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class VehicleLookupServiceImpl implements VehicleLookupService {

    private static final String CATALOG_API_BASE_URL = "http://server3.webbdealer.com:8080/vehicle-catalog";
    private static final Logger logger = LoggerFactory.getLogger(VehicleLookupServiceImpl.class);

    @Override
    public CatalogApiResponse lookupByVin(String vin) throws ApiServiceException {

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("vin", vin);

        UriComponents uriComponents = UriComponentsBuilder
                .fromUri(URI.create(CATALOG_API_BASE_URL + "/vehicles/vin"))
                .queryParams(params)
                .build();

        MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
        postParams.add("vin", vin);
        ResponseEntity<CatalogApiResponse> responseEntity = callApiService(vin);

        CatalogApiResponse apiResponse = responseEntity.getBody();

        logger.info(apiResponse.toString());

        logger.info("Catalog API Success!");

//        return convertToApiVehicle(vinAuditVehicle);
        return apiResponse;
    }

    @Override
    public CatalogApiResponse lookupByYearMakeModel(String year, String make, String model) {
        return null;
    }

	@Override
	public CatalogApiResponse lookupByYearMakeModelTrim(String year, String make, String model, String trim) {

        return null;
	}

	@Override
	public CatalogApiResponse lookupByApiId(String id) {
        return null;
    }

    private ResponseEntity<CatalogApiResponse> callApiService(String vin) {
//        RequestEntity<?> requestEntity = RequestEntity
//                .post(CATALOG_API_BASE_URL + "/vehicles/vin", params)
//                .accept(MediaType.APPLICATION_JSON)
//                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        VinFormRequest formRequest = new VinFormRequest();
        formRequest.setVin(vin);
        HttpEntity<VinFormRequest> request = new HttpEntity<>(formRequest, headers);
        CatalogApiResponse response =
                restTemplate.postForObject(CATALOG_API_BASE_URL + "/vehicles/vin", request, CatalogApiResponse.class);
        return ResponseEntity.ok(response);
    }

//    private VinAuditVehicleAdapter convertToApiVehicle(VinAuditVehicle vinAuditVehicle) {
//
//        VinAuditVehicleAdapter vehicleAdapter = new VinAuditVehicleAdapter(vinAuditVehicle);
//        logger.info("VinAuditVehicleAdapter output: " + vehicleAdapter.toString());
//        return vehicleAdapter;
//    }

}
