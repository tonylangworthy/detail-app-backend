package com.webbdealer.detailing.recondition.dao;

import com.webbdealer.detailing.company.dao.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReconditionRepository extends PagingAndSortingRepository<Recondition, Long> {

    Page<Recondition> findAllByCompanyId(Long companyId, Pageable pageable);
}
