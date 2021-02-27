package com.webbdealer.detailing.vehicle.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MakeRepository extends CrudRepository<Make, Long> {

    Optional<Make> findByNameIgnoreCase(String name);

    List<Make> findAll(Sort sort);
}
