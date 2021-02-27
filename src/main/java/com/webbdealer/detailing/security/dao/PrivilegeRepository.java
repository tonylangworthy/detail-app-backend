package com.webbdealer.detailing.security.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {

    Optional<Privilege> findByName(String name);

    boolean existsByName(String privilegeName);
}
