package com.webbdealer.detailing.timeclock.dao;

import com.webbdealer.detailing.employee.dao.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TimeClockRepository extends JpaRepository<TimeClock, Long> {

//    @Query("select t from TimeClock t where t.user.id = ?1")
    List<TimeClock> findAllByUserId(Long userId);

    @Query(value = "select * from timeclock t inner join clocked_reasons on t.clocked_reason_id = clocked_reasons.id " +
            "where user_id = ?1 and DATE(t.clocked_at) = CURRENT_DATE order by t.clocked_at asc",
            nativeQuery = true)
    List<TimeClock> findAllByUserIdAndToday(Long userId);

    @Query(value = "select * from timeclock t inner join clocked_reasons on t.clocked_reason_id = clocked_reasons.id " +
            "where DATE(t.clocked_at) = ?1 AND company_id = ?2 order by t.clocked_at asc",
            nativeQuery = true)
    List<TimeClock> findByClockedAtDate(LocalDate clockedAt, Long companyId);

    @Query(value = "select * from timeclock t inner join clocked_reasons on t.clocked_reason_id = clocked_reasons.id " +
            "where DATE(t.clocked_at) = ?1",
            nativeQuery = true)
    List<TimeClock> findByClockedAt(LocalDate clockedAt);

    @Query(value = "select * from timeclock t inner join clocked_reasons on t.clocked_reason_id = clocked_reasons.id " +
            "where user_id = ?1 and DATE(t.clocked_at) = ?2 order by user_id, clocked_at",
            nativeQuery = true)
    List<TimeClock> findByUserIdAndClockedDate(Long userId, LocalDate clockedDate);
}
