package com.webbdealer.detailing.timeclock;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.timeclock.dao.TimeClock;
import com.webbdealer.detailing.timeclock.dto.ClockedEmployeeStatusResponse;
import com.webbdealer.detailing.timeclock.dto.TimeClockRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeClockService {

    void punchTimeClock(Long userId, TimeClockRequest timeClockRequest);

    void clockInAt(TimeClockRequest timeClockRequest);

    void clockOutAt(TimeClockRequest timeClockRequest);

    List<ClockedEmployeeStatusResponse> listClockedInEmployees();

    List<ClockedEmployeeStatusResponse> listClockedOutEmployees();

    boolean isEmployeeClockedIn(Long userId);

    Optional<TimeClock> fetchLastClockEntryByEmployee(Long userId);

    ClockedEmployeeStatusResponse fetchClockedEmployeeStatus(Long userId);

    List<TimeClock> fetchTimeClockListByDate(LocalDate date);

    List<TimeClock> fetchTimeClockListByEmployee(Long userId);
}
