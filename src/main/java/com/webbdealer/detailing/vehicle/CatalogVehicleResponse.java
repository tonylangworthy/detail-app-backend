package com.webbdealer.detailing.vehicle;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Objects;

public class CatalogVehicleResponse implements Serializable {

    private String vehicleId;

//    private String vin;

    private String year;

    private String make;

    private String model;

    private String trim;

    private String style;

    private String bodystyle;

    private String size;

    private String category;

    private String numOfDoors;

    private String fuelType;

    private String fuelCapacity;

    private String mpgCity;

    private String mpgHwy;

    private String engineName;

    private String engineSize;

    private String engineCylinders;

    private String transmissionName;

    private String transmissionType;

    private String transmissionGears;

    private String drivenWheels;

    private String antiBrakeSystem;

    private String steeringType;

    private String curbWeight;

    private String grossWeight;

    private String overallHeight;

    private String overallLength;

    private String overallWidth;

    private String wheelbaseLength;

    private String standardSeating;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

//    public String getVin() {
//        return vin;
//    }
//
//    public void setVin(String vin) {
//        this.vin = vin;
//    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBodystyle() {
        return bodystyle;
    }

    public void setBodystyle(String bodystyle) {
        this.bodystyle = bodystyle;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumOfDoors() {
        return numOfDoors;
    }

    public void setNumOfDoors(String numOfDoors) {
        this.numOfDoors = numOfDoors;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(String fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public String getMpgCity() {
        return mpgCity;
    }

    public void setMpgCity(String mpgCity) {
        this.mpgCity = mpgCity;
    }

    public String getMpgHwy() {
        return mpgHwy;
    }

    public void setMpgHwy(String mpgHwy) {
        this.mpgHwy = mpgHwy;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(String engineSize) {
        this.engineSize = engineSize;
    }

    public String getEngineCylinders() {
        return engineCylinders;
    }

    public void setEngineCylinders(String engineCylinders) {
        this.engineCylinders = engineCylinders;
    }

    public String getTransmissionName() {
        return transmissionName;
    }

    public void setTransmissionName(String transmissionName) {
        this.transmissionName = transmissionName;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getTransmissionGears() {
        return transmissionGears;
    }

    public void setTransmissionGears(String transmissionGears) {
        this.transmissionGears = transmissionGears;
    }

    public String getDrivenWheels() {
        return drivenWheels;
    }

    public void setDrivenWheels(String drivenWheels) {
        this.drivenWheels = drivenWheels;
    }

    public String getAntiBrakeSystem() {
        return antiBrakeSystem;
    }

    public void setAntiBrakeSystem(String antiBrakeSystem) {
        this.antiBrakeSystem = antiBrakeSystem;
    }

    public String getSteeringType() {
        return steeringType;
    }

    public void setSteeringType(String steeringType) {
        this.steeringType = steeringType;
    }

    public String getCurbWeight() {
        return curbWeight;
    }

    public void setCurbWeight(String curbWeight) {
        this.curbWeight = curbWeight;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getOverallHeight() {
        return overallHeight;
    }

    public void setOverallHeight(String overallHeight) {
        this.overallHeight = overallHeight;
    }

    public String getOverallLength() {
        return overallLength;
    }

    public void setOverallLength(String overallLength) {
        this.overallLength = overallLength;
    }

    public String getOverallWidth() {
        return overallWidth;
    }

    public void setOverallWidth(String overallWidth) {
        this.overallWidth = overallWidth;
    }

    public String getWheelbaseLength() {
        return wheelbaseLength;
    }

    public void setWheelbaseLength(String wheelbaseLength) {
        this.wheelbaseLength = wheelbaseLength;
    }

    public String getStandardSeating() {
        return standardSeating;
    }

    public void setStandardSeating(String standardSeating) {
        this.standardSeating = standardSeating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogVehicleResponse that = (CatalogVehicleResponse) o;
        return Objects.equals(vehicleId, that.vehicleId) && Objects.equals(year, that.year) && Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(trim, that.trim) && Objects.equals(style, that.style) && Objects.equals(bodystyle, that.bodystyle) && Objects.equals(size, that.size) && Objects.equals(category, that.category) && Objects.equals(numOfDoors, that.numOfDoors) && Objects.equals(fuelType, that.fuelType) && Objects.equals(fuelCapacity, that.fuelCapacity) && Objects.equals(mpgCity, that.mpgCity) && Objects.equals(mpgHwy, that.mpgHwy) && Objects.equals(engineName, that.engineName) && Objects.equals(engineSize, that.engineSize) && Objects.equals(engineCylinders, that.engineCylinders) && Objects.equals(transmissionName, that.transmissionName) && Objects.equals(transmissionType, that.transmissionType) && Objects.equals(transmissionGears, that.transmissionGears) && Objects.equals(drivenWheels, that.drivenWheels) && Objects.equals(antiBrakeSystem, that.antiBrakeSystem) && Objects.equals(steeringType, that.steeringType) && Objects.equals(curbWeight, that.curbWeight) && Objects.equals(grossWeight, that.grossWeight) && Objects.equals(overallHeight, that.overallHeight) && Objects.equals(overallLength, that.overallLength) && Objects.equals(overallWidth, that.overallWidth) && Objects.equals(wheelbaseLength, that.wheelbaseLength) && Objects.equals(standardSeating, that.standardSeating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, year, make, model, trim, style, bodystyle, size, category, numOfDoors, fuelType, fuelCapacity, mpgCity, mpgHwy, engineName, engineSize, engineCylinders, transmissionName, transmissionType, transmissionGears, drivenWheels, antiBrakeSystem, steeringType, curbWeight, grossWeight, overallHeight, overallLength, overallWidth, wheelbaseLength, standardSeating);
    }

    @Override
    public String toString() {
        return "CatalogApiResponse{" +
                "vehicleId=" + vehicleId +
                ", year='" + year + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", trim='" + trim + '\'' +
                ", style='" + style + '\'' +
                ", bodystyle='" + bodystyle + '\'' +
                ", size='" + size + '\'' +
                ", category='" + category + '\'' +
                ", numOfDoors='" + numOfDoors + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", fuelCapacity='" + fuelCapacity + '\'' +
                ", mpgCity='" + mpgCity + '\'' +
                ", mpgHwy='" + mpgHwy + '\'' +
                ", engineName='" + engineName + '\'' +
                ", engineSize='" + engineSize + '\'' +
                ", engineCylinders='" + engineCylinders + '\'' +
                ", transmissionName='" + transmissionName + '\'' +
                ", transmissionType='" + transmissionType + '\'' +
                ", transmissionGears='" + transmissionGears + '\'' +
                ", drivenWheels='" + drivenWheels + '\'' +
                ", antiBrakeSystem='" + antiBrakeSystem + '\'' +
                ", steeringType='" + steeringType + '\'' +
                ", curbWeight='" + curbWeight + '\'' +
                ", grossWeight='" + grossWeight + '\'' +
                ", overallHeight='" + overallHeight + '\'' +
                ", overallLength='" + overallLength + '\'' +
                ", overallWidth='" + overallWidth + '\'' +
                ", wheelbaseLength='" + wheelbaseLength + '\'' +
                ", standardSeating='" + standardSeating + '\'' +
                '}';
    }
}
