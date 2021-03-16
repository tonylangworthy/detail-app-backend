package com.webbdealer.detailing.timeclock.dao;

import com.webbdealer.detailing.DetailingAppApplication;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dao.UserRepository;
import com.webbdealer.detailing.shared.TimezoneConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // Load data

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

        Optional<User> optionalUser1 = userRepository.findById(2L);
        User user1 = optionalUser1.get();

        Optional<User> optionalUser2 = userRepository.findById(3L);
        User user2 = optionalUser2.get();

        TimezoneConverter timezoneConverter
                = new TimezoneConverter.TimezoneConverterBuilder("America/Chicago").build();

        Optional<ClockedReason> optionalClockedIn = clockedReasonRepository.findById(1L);
        ClockedReason clockedIn = optionalClockedIn.get();

        Optional<ClockedReason> optionalClockedOut = clockedReasonRepository.findById(2L);
        ClockedReason clockedOut = optionalClockedOut.get();

        Optional<ClockedReason> optionalOutToLunch = clockedReasonRepository.findById(1L);
        ClockedReason outToLunch = optionalOutToLunch.get();

        Optional<ClockedReason> optionalBackFromLunch = clockedReasonRepository.findById(1L);
        ClockedReason backFromLunch = optionalBackFromLunch.get();

        TimeClock mondayIn = new TimeClock(
                timezoneConverter.fromLocalDateTimeToUtc(LocalDateTime.parse("03/08/2021 08:02 AM", formatter)),
                clockedIn,
                "Running a little late",
                ClockedStatus.IN,
                user1
        );
//        timeClockRepository.save(mondayIn);

        TimeClock mondayOutLunch = new TimeClock(
                timezoneConverter.fromLocalDateTimeToUtc(LocalDateTime.parse("03/08/2021 11:33 AM", formatter)),
                outToLunch,
                "",
                ClockedStatus.OUT,
                user1
        );
//        timeClockRepository.save(mondayOutLunch);

        TimeClock mondayInLunch = new TimeClock(
                timezoneConverter.fromLocalDateTimeToUtc(LocalDateTime.parse("03/08/2021 12:07 PM", formatter)),
                backFromLunch,
                "",
                ClockedStatus.IN,
                user1
        );
//        timeClockRepository.save(mondayInLunch);

        TimeClock mondayOut = new TimeClock(
                timezoneConverter.fromLocalDateTimeToUtc(LocalDateTime.parse("03/08/2021 05:04 PM", formatter)),
                clockedOut,
                "",
                ClockedStatus.OUT,
                user1
        );
//        timeClockRepository.save(mondayOut);

        TimeClock mondayIn1 = new TimeClock(
                timezoneConverter.fromLocalDateTimeToUtc(LocalDateTime.parse("03/08/2021 06:10 AM", formatter)),
                clockedIn,
                "My truck is a piece of crap",
                ClockedStatus.IN,
                user2
        );
//        timeClockRepository.save(mondayIn1);

        TimeClock mondayOutLunch1 = new TimeClock(
                timezoneConverter.fromLocalDateTimeToUtc(LocalDateTime.parse("03/08/2021 11:24 AM", formatter)),
                outToLunch,
                "",
                ClockedStatus.OUT,
                user2
        );
//        timeClockRepository.save(mondayOutLunch1);

        TimeClock mondayInLunch1= new TimeClock(
                timezoneConverter.fromLocalDateTimeToUtc(LocalDateTime.parse("03/08/2021 11:58 PM", formatter)),
                backFromLunch,
                "",
                ClockedStatus.IN,
                user2
        );
//        timeClockRepository.save(mondayInLunch1);

        TimeClock mondayOut1 = new TimeClock(
                timezoneConverter.fromLocalDateTimeToUtc(LocalDateTime.parse("03/08/2021 05:58 PM", formatter)),
                clockedOut,
                "",
                ClockedStatus.OUT,
                user2
        );
//        timeClockRepository.save(mondayOut1);

        List<TimeClock> user1Time = timeClockRepository.findByUserIdAndClockedDate(user1.getId(), LocalDate.of(2021, 03, 8));
        List<TimeClock> user2Time = timeClockRepository.findByUserIdAndClockedDate(user1.getId(), LocalDate.of(2021, 03, 8));

        List<LocalTime> user1TimeList = user1Time.stream()
                .map(timeClock -> timeClock.getClockedAt().toLocalDateTime().toLocalTime())
                .collect(Collectors.toList());

        System.out.println(user1TimeList.toString());


        System.out.println(user1Time.size());
        System.out.println(user2Time.size());

    }

    @Test
    public void getAllPunchedTimeClocks_Test() {
        List<TimeClock> timeClocks = timeClockRepository.findAll();

        timeClocks.forEach(timeClock -> System.out.println(timeClock.getClockedAt()));
    }
}
