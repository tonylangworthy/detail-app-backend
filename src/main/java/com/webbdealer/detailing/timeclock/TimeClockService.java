package com.webbdealer.detailing.timeclock;

import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.timeclock.dao.TimeClock;
import com.webbdealer.detailing.timeclock.dto.ClockedEmployeeStatusResponse;
import com.webbdealer.detailing.timeclock.dto.TimeClockRequest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.Optional;

public interface TimeClockService {

    void punchTimeClock(Long userId, TimeClockRequest timeClockRequest);

    Duration totalHoursWorkedByDay(Long userId, LocalDate day);

    Duration totalHoursWorked(Long userId, TemporalAccessor temporal);

    List<ClockedEmployeeStatusResponse> listClockedInEmployees(Long companyId);

    List<ClockedEmployeeStatusResponse> listClockedOutEmployees(Long companyId);

    boolean isEmployeeClockedIn(Long userId);

    Optional<TimeClock> fetchLastClockEntryByEmployee(Long userId);

    ClockedEmployeeStatusResponse fetchClockedEmployeeStatus(Long userId);

    List<TimeClock> fetchTimeClockListByDate(LocalDate date);

    List<TimeClock> fetchTimeClockListByEmployee(Long userId);
}
