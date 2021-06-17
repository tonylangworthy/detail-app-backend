package com.webbdealer.detailing.vehicle.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {

    Page<Vehicle> findAllByCompanyId(Long companyId, Pageable pageable);

    @Query(value = "select catalog_id from vehicles where company_id = ?1", nativeQuery = true)
    Long[] findAllCatalogIdsByCompanyId(Long companyId);

    Page<Vehicle> findByCompanyIdAndCatalogIdIn(Long companyId, List<Long> catalogIds, Pageable pageable);

    Optional<Vehicle> findByVinAndCompanyId(String vin, Long companyId);
}
