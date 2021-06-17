package com.webbdealer.detailing.vehicle.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.Objects;

@Relation(collectionRelation = "vehicles")
public class VehicleItemModel extends RepresentationModel<VehicleItemModel> {

    private Long id;

    private Long catalogId;

    private String vin;

    private String year;

    private String make;

    private String model;

    private String trim;

    private String color;

    private LocalDateTime arrivedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getArrivedAt() {
        return arrivedAt;
    }

    public void setArrivedAt(LocalDateTime arrivedAt) {
        this.arrivedAt = arrivedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VehicleItemModel that = (VehicleItemModel) o;
        return Objects.equals(id, that.id) && Objects.equals(catalogId, that.catalogId) && Objects.equals(vin, that.vin) && Objects.equals(year, that.year) && Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(trim, that.trim) && Objects.equals(color, that.color) && Objects.equals(arrivedAt, that.arrivedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, catalogId, vin, year, make, model, trim, color, arrivedAt);
    }

    @Override
    public String toString() {
        return "VehicleItemModel{" +
                "id=" + id +
                ", catalogId=" + catalogId +
                ", vin='" + vin + '\'' +
                ", year='" + year + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", trim='" + trim + '\'' +
                ", color='" + color + '\'' +
                ", arrivedAt=" + arrivedAt +
                '}';
    }
}
