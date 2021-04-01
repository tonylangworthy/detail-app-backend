package com.webbdealer.detailing.job.dao;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.customer.CustomerService;
import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.InvalidJobActionException;
import com.webbdealer.detailing.job.JobService;
import com.webbdealer.detailing.job.JobServiceImpl;
import com.webbdealer.detailing.recondition.ReconditionService;
import com.webbdealer.detailing.vehicle.VehicleLookupService;
import com.webbdealer.detailing.vehicle.VehicleService;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
public class JobServiceImplIntegrationTests {

    private Job job;
    private User user1, user2, user3;

    @Mock
    private CompanyService companyService;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private VehicleService vehicleService;
    @Mock
    private VehicleLookupService vehicleLookupService;
    @Mock
    private CustomerService customerService;
    @Mock
    private ReconditionService reconditionService;
    @Mock
    private EmployeeService employeeService;

    private JobServiceImpl jobService;
    private LocalDate yesterday;
    private LocalDate today;
    private LocalTime yesterdayStartTime;
    private LocalTime yesterdayStopTime;
    private LocalTime todayStartTime;
    private LocalTime todayStopTime;

    @BeforeEach
    public void initJobs() {
        System.out.println("BeforeEach called");
        yesterday = LocalDate.of(2021, 03, 28);
        today = LocalDate.of(2021, 03, 29);
        yesterdayStartTime = LocalTime.of(8, 33, 0);
        yesterdayStopTime = LocalTime.of(5, 2, 0);
        todayStartTime = LocalTime.of(8, 2, 0);
        todayStopTime = LocalTime.of(11, 15, 0);

        job = new Job();
        user1 = new User();
        user1.setId(1L);
        user2 = new User();
        user2.setId(2L);
        user3 = new User();
        user3.setId(3L);


        // Every new job starts as PENDING!
        job.setJobStatus(JobStatus.PENDING);

        jobService = new JobServiceImpl(
                companyService,
                jobRepository,
                vehicleService,
                vehicleLookupService,
                customerService,
                reconditionService,
                employeeService);

    }

    @Test
    public void startJob_UserAlreadyAssigned_InvalidJobActionException_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.STOP, job, user1));
//        jobActionList.add(new JobAction(LocalDateTime.of(today, todayStartTime), Action.START, job, user2));
//        jobActionList.add(new JobAction(LocalDateTime.of(today, todayStopTime), Action.STOP, job, user1));
        job.setJobActions(jobActionList);

        assertThrows(InvalidJobActionException.class, () -> {
            jobService.startJob(job, user1.getId(), LocalDateTime.of(2021, 03, 30, 8, 30, 0));
        });


//        assertEquals(JobStatus.ACTIVE, newJob.getJobStatus());
    }

    @Test
    public void startJob_AlreadyStarted_InvalidJobActionException_Test() {

        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.STOP, job, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(today, todayStartTime), Action.START, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(today, todayStopTime), Action.STOP, job, user1));
        job.setJobActions(jobActionList);

        assertThrows(InvalidJobActionException.class, () -> {
            jobService.startJob(job, user1.getId(), LocalDateTime.of(2021, 03, 30, 8, 30, 0));
        });

        fail();
    }

    @Test
    public void stopJob_Test() {
        fail();
    }

    @Test
    public void pauseJob_Test() {
        fail();
    }

    @Test
    public void resumeJob_Test() {
        fail();
    }

    @Test
    public void cancelJob_Test() {
        fail();
    }


}
