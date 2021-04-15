package com.webbdealer.detailing.job.dao;

import com.webbdealer.detailing.employee.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobActionRepository extends JpaRepository<JobAction, Long> {

    List<JobAction> findByJobId(Long jobId);

    List<JobAction> findByUser(User user);
}
