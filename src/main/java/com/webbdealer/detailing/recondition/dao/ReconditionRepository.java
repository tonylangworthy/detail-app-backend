package com.webbdealer.detailing.recondition.dao;

import com.webbdealer.detailing.company.dao.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReconditionRepository extends JpaRepository<Recondition, Long> {

    List<Recondition> findAllByCompanyId(Long companyId);
}
