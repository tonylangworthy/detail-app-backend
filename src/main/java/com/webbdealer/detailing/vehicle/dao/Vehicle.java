package com.webbdealer.detailing.vehicle.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.shared.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vehicles")
public class Vehicle extends BaseEntity implements Serializable {

    @Column(name = "vin", unique = true)
    private String vin;

    @Column(name = "catalog_id")
	private Long catalogId;

    @Column(name = "color")
    private String color;

    @Column(name = "arrival_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime arrivalDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    private Company company;

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

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

	public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Vehicle)) return false;
		if (!super.equals(o)) return false;
		Vehicle vehicle = (Vehicle) o;
		return Objects.equals(vin, vehicle.vin) &&
				Objects.equals(catalogId, vehicle.catalogId) &&
				Objects.equals(color, vehicle.color) &&
				Objects.equals(arrivalDate, vehicle.arrivalDate) &&
				Objects.equals(company, vehicle.company);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), vin, catalogId, color, arrivalDate, company);
	}

	@Override
	public String toString() {
		return "Vehicle{" +
				"vin='" + vin + '\'' +
				", catalogId=" + catalogId +
				", color='" + color + '\'' +
				", arrivalDate=" + arrivalDate +
				'}';
	}

}
