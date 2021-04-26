package com.webbdealer.detailing.timeclock;

import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dao.UserRepository;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.shared.TimezoneConverter;
import com.webbdealer.detailing.timeclock.dao.*;
import com.webbdealer.detailing.timeclock.dto.ClockedEmployeeStatusResponse;
import com.webbdealer.detailing.timeclock.dto.TimeClockRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeClockServiceImpl implements TimeClockService {

    private static final Logger logger = LoggerFactory.getLogger(TimeClockServiceImpl.class);

    private TimeClockRepository timeClockRepository;

    private ClockedReasonRepository clockedReasonRepository;

    private UserRepository userRepository;

    @Autowired
    public TimeClockServiceImpl(TimeClockRepository timeClockRepository,
                                ClockedReasonRepository clockedReasonRepository,
                                UserRepository userRepository) {
        this.timeClockRepository = timeClockRepository;
        this.clockedReasonRepository = clockedReasonRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void punchTimeClock(Long userId, TimeClockRequest timeClockRequest) {
//        TimezoneConverter timezoneConverter
//                = new TimezoneConverter.TimezoneConverterBuilder("America/Chicago").build();
        User user = userRepository.getOne(userId);
        ClockedReason clockedReason = clockedReasonRepository.getOne(timeClockRequest.getClockedReasonId());

        TimeClock timeClock = new TimeClock();
        timeClock.setUser(user);
        timeClock.setClockedReason(clockedReason);

        LocalDateTime clockedAt = LocalDateTime.of(timeClockRequest.getClockedAtDate(), timeClockRequest.getClockedAtTime());
//        timeClock.setClockedAt(timezoneConverter.fromLocalDateTimeToUtc(clockedAt));
        timeClock.setClockedAt(clockedAt);

        timeClock.setClockedStatus(timeClockRequest.getClockedStatus());
        TimeClock newClockedTime = timeClockRepository.save(timeClock);
        logger.info(newClockedTime.toString());
    }

    @Override
    public Duration totalHoursWorkedByDay(Long userId, LocalDate day) {

        List<TimeClock> employeeTime = timeClockRepository.findByUserIdAndClockedDate(userId, day);

        List<LocalTime> user1TimeList = employeeTime.stream()
                .map(timeClock -> timeClock.getClockedAt().toLocalTime())
                .collect(Collectors.toList());

        ListIterator<TimeClock> listIterator = employeeTime.listIterator();

        while(listIterator.hasNext()) {
            System.out.println("current index: "+ listIterator.previousIndex() +" -- next index: " + listIterator.nextIndex());

            TimeClock timeClockIn = listIterator.next();
            System.out.println("clocked in: " + timeClockIn.getClockedAt().toString());

            if(listIterator.hasNext()) {
                TimeClock timeClockOut = listIterator.next();
                System.out.println("clocked out: " + timeClockOut.getClockedAt().toString());

                Optional<LocalTime> optionalClockedInAt = Optional.empty();
                Optional<LocalTime> optionalClockedOutAt = Optional.empty();

                if(timeClockIn.getClockedStatus().equals(ClockedStatus.IN)) {
                    optionalClockedInAt = Optional.of(timeClockIn.getClockedAt().toLocalTime());
                }
                if(timeClockOut.getClockedStatus().equals(ClockedStatus.OUT)) {
                    optionalClockedOutAt = Optional.of(timeClockOut.getClockedAt().toLocalTime());
                }

                if(optionalClockedInAt.isPresent() && optionalClockedOutAt.isPresent()) {
                    LocalTime clockedInAt = optionalClockedInAt.get();
                    LocalTime clockedOutAt = optionalClockedOutAt.get();

                    System.out.println("Is after: " + clockedOutAt.isAfter(clockedInAt));

                    System.out.println("until: " + clockedInAt.until(clockedOutAt, ChronoUnit.MINUTES));
                    int hours = Duration.between(clockedInAt, clockedOutAt).toHoursPart();
                    int minutes = Duration.between(clockedInAt, clockedOutAt).toMinutesPart();
                    System.out.println(hours + " hours, " + minutes + " minutes");

                }

            }


        }


        return null;
    }

    @Override
    public Duration totalHoursWorked(Long userId, TemporalAccessor temporal) {

        List<TimeClock> user1Time = timeClockRepository.findByUserIdAndClockedDate(userId, LocalDate.of(2021, 03, 8));


        return null;
    }

    @Override
    public List<ClockedEmployeeStatusResponse> listClockedInEmployees() {
        List<TimeClock> clockedIn = timeClockRepository.findByClockedAtDate(LocalDate.now());
        return buildEmployeeStatusList(clockedIn);
    }

    @Override
    public List<ClockedEmployeeStatusResponse> listClockedOutEmployees() {

        List<TimeClock> clockedOut = timeClockRepository.findByClockedAtDate(LocalDate.now());
        return buildEmployeeStatusList(clockedOut);
    }

    @Override
    @Transactional
    public boolean isEmployeeClockedIn(Long userId) {
        // find all records
        // 1. by userId
        // 2. that are today
        // 3. the last clocked date
        // 4. clocked in is true
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));

        Optional<TimeClock> optionalLastClockEntry = fetchLastClockEntryByEmployee(userId);

        if(optionalLastClockEntry.isPresent()) {
            ClockedStatus status = optionalLastClockEntry.get().getClockedStatus();

            if(status.equals(ClockedStatus.IN)) return true;
        }
        return false;
    }

    @Override
    public Optional<TimeClock> fetchLastClockEntryByEmployee(Long userId) {
        List<TimeClock> clockedInToday = timeClockRepository.findAllByUserIdAndToday(userId);
        int numberOfClockEntries = clockedInToday.size();
        if(numberOfClockEntries == 0) {
            return Optional.empty();
        }

        // Get the latest clock entry
        return Optional.of(clockedInToday.get(numberOfClockEntries-1));
    }

    @Override
    public ClockedEmployeeStatusResponse fetchClockedEmployeeStatus(Long userId) {
        Optional<TimeClock> optionalLastClockEntry = fetchLastClockEntryByEmployee(userId);
        if(!optionalLastClockEntry.isPresent()) {
            throw new TimeClockEntriesNotFoundException("Employee with id of ["+userId+"] has not clocked in today.");
        }
        return mapTimeClockToClockedEmployee(optionalLastClockEntry.get());
    }

    @Override
    public List<TimeClock> fetchTimeClockListByDate(LocalDate date) {

        List<TimeClock> clockList = timeClockRepository.findByClockedAt(date);
        return clockList;
    }

    @Override
    @Transactional
    public List<TimeClock> fetchTimeClockListByEmployee(Long userId) {
        return timeClockRepository.findAllByUserId(userId);
    }

    private ClockedEmployeeStatusResponse mapTimeClockToClockedEmployee(TimeClock timeClock) {
//        TimezoneConverter timezoneConverter
//                = new TimezoneConverter.TimezoneConverterBuilder("America/Chicago").build();
        ClockedEmployeeStatusResponse clockedEmployee = new ClockedEmployeeStatusResponse();
        clockedEmployee.setId(timeClock.getId());
        clockedEmployee.setClockedAt(timeClock.getClockedAt());
        clockedEmployee.setClockedReason(timeClock.getClockedReason().getName());
        clockedEmployee.setClockedStatus(timeClock.getClockedStatus());
        clockedEmployee.setEmployeeNote(timeClock.getEmployeeNote());
        return clockedEmployee;
    }

    private List<ClockedEmployeeStatusResponse> buildEmployeeStatusList(List<TimeClock> timeClockList) {
        List<ClockedEmployeeStatusResponse> employeeList = new ArrayList<>();
        timeClockList.forEach(clocked -> {
            employeeList.add(mapTimeClockToClockedEmployee(clocked));
        });
        return employeeList;
    }
}
