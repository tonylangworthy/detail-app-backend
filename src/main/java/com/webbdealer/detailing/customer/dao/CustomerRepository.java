package com.webbdealer.detailing.customer.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Optional<Customer> findByCompanyIdAndId(Long companyId, Long id);

    Optional<Customer> findByFirstNameAndLastNameAndPhone(String firstName, String lastName, String phone);

    Page<Customer> findByCompanyIdAndCustomerType(Long companyId, CustomerType customerType, Pageable pageable);

}
