package com.webbdealer.detailing.vehicle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByCompanyId(Long companyId);

    Optional<Vehicle> findByVinAndCompanyId(String vin, Long companyId);
}
