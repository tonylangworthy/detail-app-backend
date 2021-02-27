package com.webbdealer.detailing.timeclock;

import com.webbdealer.detailing.employee.dao.UserRepository;
import com.webbdealer.detailing.timeclock.dao.ClockedReason;
import com.webbdealer.detailing.timeclock.dao.TimeClock;
import com.webbdealer.detailing.timeclock.dao.TimeClockRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TimeClockServiceIntegrationTests {

    @Autowired
    private TimeClockService timeClockService;

    @Test
//    @Transactional
    public void isEmployeeClockedIn_Test() {

//        boolean isClockedIn = timeClockService.isEmployeeClockedIn(2L);
//
//        assertFalse(isClockedIn);
    }

    @Test
//    @Transactional
    public void fetchTimeClockByEmployee_Test() {
//        List<TimeClock> timeClocks = timeClockService.fetchTimeClockListByEmployee(2L);
//
//        timeClocks.forEach((timeClock) -> System.out.println(timeClock.getClockedAt() + ": " + timeClock.getClockedReason().getName()));
    }

    @Test
    public void fetchTimeClockListByDate_Test() {
//        ZoneId zoneId = ZoneId.of("UTC");
//        LocalDate timeClockDate = LocalDate.of(2021, 2, 20);
//        List<TimeClock> timeClockList = timeClockService.fetchTimeClockListByDate(timeClockDate);
//
//        assertTrue(timeClockList.size() > 0);
    }
}
