package com.webbdealer.detailing.timeclock.dao;

import com.webbdealer.detailing.DetailingAppApplication;
import com.webbdealer.detailing.employee.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class TimeClockRepositoryIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TimeClockRepository timeClockRepository;


    @Test
    @Transactional
    public void getTimeClockByDate_Test() {

//        System.out.println(timeClockRepository.findAll());
    }
}
