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
    private LocalTime yesterdayPauseTime;
    private LocalTime yesterdayResumeTime;
    private LocalTime todayStartTime;
    private LocalTime todayStopTime;
    private LocalTime todayPauseTime;
    private LocalTime todayResumeTime;

    @BeforeEach
    public void initJobs() {
        System.out.println("BeforeEach called");
        yesterday = LocalDate.of(2021, 03, 28);
        today = LocalDate.of(2021, 03, 29);
        yesterdayStartTime = LocalTime.of(8, 33, 0);
        yesterdayStopTime = LocalTime.of(5, 2, 0);
        yesterdayPauseTime = LocalTime.of(11, 32, 0);
        yesterdayResumeTime = LocalTime.of(12, 3, 0);
        todayStartTime = LocalTime.of(8, 2, 0);
        todayStopTime = LocalTime.of(11, 15, 0);
        todayPauseTime = LocalTime.of(11, 25, 0);
        todayResumeTime = LocalTime.of(11, 58, 0);

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
        jobActionList.add(new JobAction(LocalDateTime.of(today, todayStartTime), Action.START, job, user2));
        job.setJobActions(jobActionList);

        assertThrows(InvalidJobActionException.class, () -> {
            jobService.startJob(job, user1, LocalDateTime.of(2021, 03, 30, 8, 30, 0));
        });
    }

    @Test
    public void startJob_AlreadyStarted_InvalidJobActionException_Test() {

        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.STOP, job, user2));
        job.setJobActions(jobActionList);

        assertThrows(InvalidJobActionException.class, () -> {
            jobService.startJob(job, user1, LocalDateTime.of(2021, 03, 30, 8, 30, 0));
        });
    }

    @Test
    public void startJob_JobStatusActive_Test() {

        // If job is ACTIVE, use the addEmployeeToJob method instead
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayPauseTime), Action.PAUSE, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayResumeTime), Action.RESUME, job, user2));
        job.setJobActions(jobActionList);

        Job updatedJob = jobService.startJob(job, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.ACTIVE, updatedJob.getJobStatus());
    }

    @Test
    public void numberOfEmployeesOnJob_Equals2_Test() {

        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.STOP, job, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(today, todayStartTime), Action.START, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(today, todayPauseTime), Action.PAUSE, job, user2));
        job.setJobActions(jobActionList);

        assertEquals(2, jobService.numberOfEmployeesOnJob(jobActionList));
    }

    @Test
    public void stopJob_NotStarted_InvalidJobActionException_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayPauseTime), Action.PAUSE, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayResumeTime), Action.RESUME, job, user2));
        job.setJobActions(jobActionList);

        assertThrows(InvalidJobActionException.class, () -> {
            jobService.stopJob(job, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));
        });
    }

    @Test
    public void stopJob_IncorrectUser_InvalidJobActionException_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayPauseTime), Action.PAUSE, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayResumeTime), Action.RESUME, job, user2));
        job.setJobActions(jobActionList);

        assertThrows(InvalidJobActionException.class, () -> {
            jobService.stopJob(job, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));
        });
    }

    @Test
    public void stopJob_IncorrectJobStatus_InvalidJobActionException_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayPauseTime), Action.PAUSE, job, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayResumeTime), Action.RESUME, job, user2));
        job.setJobActions(jobActionList);

        assertThrows(InvalidJobActionException.class, () -> {
            jobService.stopJob(job, user2, LocalDateTime.of(2021, 3, 31, 6, 30, 0));
        });
    }

    @Test
    public void stopJob_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayPauseTime), Action.PAUSE, job, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayResumeTime), Action.RESUME, job, user1));
        job.setJobActions(jobActionList);
        job.setJobStatus(JobStatus.ACTIVE);

        Job updatedJob = jobService.stopJob(job, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.COMPLETED, updatedJob.getJobStatus());
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
