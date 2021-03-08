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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleLookupServiceImpl implements VehicleLookupService {

    private static final String CATALOG_API_BASE_URL = "https://catalog.webbdealer.com";
    private static final Logger logger = LoggerFactory.getLogger(VehicleLookupServiceImpl.class);

    @Override
    public ResponseEntity<CatalogVehicleResponse[]> lookupByCatalogIds(List<Long> idList) {

        String commaSeparatedIdList = commaSeparatedIdList(idList);

        final String uri = CATALOG_API_BASE_URL + "/vehicles/"+commaSeparatedIdList;

        RestTemplate restTemplate = new RestTemplate();
        CatalogVehicleResponse[] apiResponse = restTemplate.getForObject(uri, CatalogVehicleResponse[].class);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public CatalogVehicleResponse lookupByVin(String vin) throws ApiServiceException {

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("vin", vin);

        UriComponents uriComponents = UriComponentsBuilder
                .fromUri(URI.create(CATALOG_API_BASE_URL + "/vehicles/vin"))
                .queryParams(params)
                .build();

        MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
        postParams.add("vin", vin);
        ResponseEntity<CatalogVehicleResponse> responseEntity = callApiService(vin);

        CatalogVehicleResponse apiResponse = responseEntity.getBody();

        logger.info(apiResponse.toString());

        logger.info("Catalog API Success!");

//        return convertToApiVehicle(vinAuditVehicle);
        return apiResponse;
    }

    @Override
    public CatalogVehicleResponse lookupByYearMakeModel(String year, String make, String model) {
        return null;
    }

	@Override
	public CatalogVehicleResponse lookupByYearMakeModelTrim(String year, String make, String model, String trim) {

        return null;
	}

    private ResponseEntity<CatalogVehicleResponse> callApiService(String vin) {
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
        CatalogVehicleResponse response =
                restTemplate.postForObject(CATALOG_API_BASE_URL + "/vehicles/vin", request, CatalogVehicleResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    public String commaSeparatedIdList(List<Long> idList) {
        return idList.stream()
                .map(id -> id.toString())
                .collect(Collectors.joining(","));
    }

//    private VinAuditVehicleAdapter convertToApiVehicle(VinAuditVehicle vinAuditVehicle) {
//
//        VinAuditVehicleAdapter vehicleAdapter = new VinAuditVehicleAdapter(vinAuditVehicle);
//        logger.info("VinAuditVehicleAdapter output: " + vehicleAdapter.toString());
//        return vehicleAdapter;
//    }

}
