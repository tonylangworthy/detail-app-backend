package com.webbdealer.detailing.vehicle.dto;

import java.io.Serializable;
import java.util.Objects;

public class VehicleCreateForm implements Serializable {

    private Long id;

    private String vin;

    private Long catalogId;

    private String color;

    private String arrivalDate;

    private String arrivalTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleCreateForm)) return false;
        VehicleCreateForm that = (VehicleCreateForm) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(vin, that.vin) &&
                Objects.equals(catalogId, that.catalogId) &&
                Objects.equals(color, that.color) &&
                Objects.equals(arrivalDate, that.arrivalDate) &&
                Objects.equals(arrivalTime, that.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vin, catalogId, color, arrivalDate, arrivalTime);
    }

    @Override
    public String toString() {
        return "VehicleCreateForm{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", catalogId=" + catalogId +
                ", color='" + color + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}
