package com.webbdealer.detailing.vehicle.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TrimRepository extends CrudRepository<Trim, Long> {

    Optional<Trim> findByNameIgnoreCase(String name);
   
}
