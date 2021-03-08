package com.webbdealer.detailing.vehicle.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class VehicleResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long catalogId;
	
	private String vin;
	
	private String year;
	
	private String make;
	
	private String model;
	
	private String trim;

	private String style;

	private String color;

	private LocalDateTime arrivedAt;
	
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VehicleResponse that = (VehicleResponse) o;
		return Objects.equals(id, that.id) && Objects.equals(catalogId, that.catalogId) && Objects.equals(vin, that.vin) && Objects.equals(year, that.year) && Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(trim, that.trim) && Objects.equals(style, that.style) && Objects.equals(color, that.color) && Objects.equals(arrivedAt, that.arrivedAt) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, catalogId, vin, year, make, model, trim, style, color, arrivedAt, createdAt, updatedAt);
	}

	@Override
	public String toString() {
		return "VehicleResponse{" +
				"id=" + id +
				", catalogId=" + catalogId +
				", vin='" + vin + '\'' +
				", year='" + year + '\'' +
				", make='" + make + '\'' +
				", model='" + model + '\'' +
				", trim='" + trim + '\'' +
				", style='" + style + '\'' +
				", color='" + color + '\'' +
				", arrivedAt=" + arrivedAt +
				", createdAt='" + createdAt + '\'' +
				", updatedAt='" + updatedAt + '\'' +
				'}';
	}


}
