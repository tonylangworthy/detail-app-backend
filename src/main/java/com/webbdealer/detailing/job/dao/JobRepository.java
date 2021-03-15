package com.webbdealer.detailing.job.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByCompanyIdAndId(Long companyId, Long jobId);

    List<Job> findAllByCompanyId(Long companyId);

    @Query(value = "select * from jobs j where j.company_id = ?1 and j.job_canceled = false " +
            "and j.job_started_at is null " +
            "and j.job_paused_at is null " +
            "and j.job_ended_at is null", nativeQuery = true)
    List<Job> findPendingJobs(Long companyId);
}
