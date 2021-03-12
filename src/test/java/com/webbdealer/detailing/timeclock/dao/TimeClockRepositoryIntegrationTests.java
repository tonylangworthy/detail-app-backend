package com.webbdealer.detailing.timeclock.dao;

import com.webbdealer.detailing.DetailingAppApplication;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class TimeClockRepositoryIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimeClockRepository timeClockRepository;

    @Autowired
    private ClockedReasonRepository clockedReasonRepository;

    @Test
    @Transactional
    public void getTimeClockByDate_Test() {

//        System.out.println(timeClockRepository.findAll());
    }

    @Test
    public void punchTimeClock_Test() {

//        Optional<User> optionalUser = userRepository.findById(2L);
//        Optional<ClockedReason> optionalClockedReason = clockedReasonRepository.findById(2L);
//        ClockedReason reason = optionalClockedReason.orElseThrow();
//
//
//        System.out.println(reason.getName());
//
//        if(optionalUser.isPresent()) {
//            LocalDateTime dateTime = LocalDateTime.now();
//            System.out.println(dateTime);
//
//            TimeClock timeClock = new TimeClock();
//            timeClock.setUser(optionalUser.get());
//            timeClock.setClockedReason(reason);
//            timeClock.setClockedAt(dateTime);
//            timeClock.setClockedStatus(ClockedStatus.OUT);
////            timeClockRepository.save(timeClock);
//
//        }


    }

    @Test
    public void getAllPunchedTimeClocks_Test() {
        List<TimeClock> timeClocks = timeClockRepository.findAll();

        timeClocks.forEach(timeClock -> System.out.println(timeClock.getClockedAt()));
    }
}
