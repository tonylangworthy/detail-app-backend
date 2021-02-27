package com.webbdealer.detailing.vehicle;

import java.util.Set;

/**
 * Interface for providing vehicle attributes.
 */
public interface VehicleProvider {

    String getVin();

    String getYear();

    String getMake();

    String getModel();

    String getTrim();
    
    String getStyle();

    Set<String> getTrims();

    String getBodystyle();

    String getSize();

    String getCategory();

    String getMadeIn();

    String getMadeInCity();

    String getNumOfDoors();

    String getFuelType();

    String getFuelCapacity();

    String getMpgCity();

    String getMpgHighway();

    String getEngine();

    String getEngineSize();

    String getEngineCylinders();

    String getTransmission();

    String getTransmissionType();

    String getTransmissionSpeeds();

    String getDrivetrain();

    String getAbs();

    String getSteeringType();

    String getCurbWeight();

    String getGrossVehicleWeightRating();

    String getOverallHeight();

    String getOverallLength();

    String getOverallWidth();

    String getWheelbaseLength();

    String getStandardSeating();

    String getInvoicePrice();

    String getDeliveryCharges();

    String getMsrp();

}
