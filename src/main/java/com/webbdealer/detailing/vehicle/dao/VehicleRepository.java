package com.webbdealer.detailing.vehicle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByCompanyId(Long companyId);

    @Query(value = "select catalog_id from vehicles where company_id = ?1", nativeQuery = true)
    Long[] findAllCatalogIdsByCompanyId(Long companyId);

    List<Vehicle> findByCompanyIdAndCatalogIdIn(Long companyId, List<Long> catalogIds);

    Optional<Vehicle> findByVinAndCompanyId(String vin, Long companyId);
}
