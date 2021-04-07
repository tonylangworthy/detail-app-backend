package com.webbdealer.detailing.job.dao;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.customer.CustomerService;
import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.*;
import com.webbdealer.detailing.recondition.ReconditionService;
import com.webbdealer.detailing.vehicle.VehicleLookupService;
import com.webbdealer.detailing.vehicle.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
public class JobServiceImplIntegrationTests {

    private Job job1, job2;
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
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private JobActionRepository jobActionRepository;


    private JobServiceImpl jobService;
    private JobActionServiceImpl jobActionService;
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

        job1 = new Job();
        job2 = new Job();
        user1 = new User();
        user1.setId(1L);
        user2 = new User();
        user2.setId(2L);
        user3 = new User();
        user3.setId(3L);


        // Every new job starts as PENDING!
        job1.setJobStatus(JobStatus.PENDING);
        // This job is already active
        job2.setJobStatus(JobStatus.ACTIVE);

        jobActionService = new JobActionServiceImpl(publisher, jobActionRepository, employeeService);

        jobService = new JobServiceImpl(
                companyService,
                jobRepository,
                jobActionService,
                vehicleService,
                vehicleLookupService,
                customerService,
                reconditionService,
                employeeService);

    }

    @Test
    public void startJob_UserAlreadyAssigned_InvalidJobActionException_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        job1.setJobActions(jobActionList);

        assertThrows(InvalidJobActionException.class, () -> {
            jobService.startJob(job1, user1, LocalDateTime.of(2021, 03, 30, 8, 30, 0));
        });
    }

    @Test
    public void startJob_JobStatusActive_Test() {

        // If job is ACTIVE, use the addEmployeeToJob method instead
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user2));
        job1.setJobActions(jobActionList);

        Job updatedJob = jobService.startJob(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.ACTIVE, updatedJob.getJobStatus());
    }

    @Test
    public void numberOfEmployeesOnJob_Equals2_Test() {

        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.STOP, job1, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(today, todayStartTime), Action.START, job1, user2));
        job1.setJobActions(jobActionList);

        assertEquals(2, jobService.numberOfEmployeesOnJob(jobActionList));
    }

    @Test
    public void markJobAsFinished_NotStarted_InvalidJobStatusException_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user2));
        job1.setJobActions(jobActionList);

        assertThrows(InvalidJobStatusException.class, () -> {
            jobService.markJobAsFinished(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));
        });
    }

    @Test
    public void stopJob_IncorrectJobStatus_InvalidJobActionException_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayPauseTime), Action.STOP, job1, user2));
        job1.setJobActions(jobActionList);

        assertThrows(InvalidJobStatusException.class, () -> {
            jobService.markJobAsFinished(job1, user2, LocalDateTime.of(2021, 3, 31, 6, 30, 0));
        });
    }

    @Test
    public void markJobAsFinished_TwoEmployees_EqualsJobStatusActive() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user2));
        jobActionList.add(new JobAction(LocalDateTime.of(today, todayStartTime), Action.START, job1, user1));
        job1.setJobActions(jobActionList);
        job1.setJobStatus(JobStatus.ACTIVE);

        Job job = jobService.markJobAsFinished(job1, user1, LocalDateTime.of(2021, 4, 5, 5, 1, 0));

        assertEquals(JobStatus.ACTIVE, job.getJobStatus());
    }

    @Test
    public void markJobAsFinished_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayPauseTime), Action.STOP, job1, user1));
        job1.setJobActions(jobActionList);
        job1.setJobStatus(JobStatus.ACTIVE);

        Job updatedJob = jobService.markJobAsFinished(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.AWAITING_APPROVAL, updatedJob.getJobStatus());
    }

    @Test
    public void pauseJob_AlreadyPaused_InvalidJobActionException_Test() {

        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        job1.setJobActions(jobActionList);
        job1.setJobStatus(JobStatus.PAUSED);

        assertThrows(InvalidJobStatusException.class, () ->
                jobService.pauseJob(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0)));
    }

    @Test
    public void pauseJob_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        job1.setJobActions(jobActionList);
        job1.setJobStatus(JobStatus.ACTIVE);

        Job updatedJob = jobService.pauseJob(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.PAUSED, updatedJob.getJobStatus());
    }

    @Test
    public void approveJob_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.START, job1, user1));
        job1.setJobActions(jobActionList);
        job1.setJobStatus(JobStatus.AWAITING_APPROVAL);

        Job updatedJob = jobService.approveJob(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.COMPLETED, updatedJob.getJobStatus());
    }

    @Test
    public void denyJob_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.START, job1, user1));
        job1.setJobActions(jobActionList);
        job1.setJobStatus(JobStatus.AWAITING_APPROVAL);

        Job updatedJob = jobService.denyJob(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.AWAITING_APPROVAL, updatedJob.getJobStatus());
    }

    @Test
    public void resumeJob_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.PAUSE, job1, user1));
        job1.setJobActions(jobActionList);
        job1.setJobStatus(JobStatus.PAUSED);

        Job updatedJob = jobService.resumeJob(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.ACTIVE, updatedJob.getJobStatus());
    }

    @Test
    public void cancelJob_Test() {
        List<JobAction> jobActionList = new ArrayList<>();
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStartTime), Action.START, job1, user1));
        jobActionList.add(new JobAction(LocalDateTime.of(yesterday, yesterdayStopTime), Action.PAUSE, job1, user1));
        job1.setJobActions(jobActionList);
        job1.setJobStatus(JobStatus.PAUSED);

        Job updatedJob = jobService.cancelJob(job1, user1, LocalDateTime.of(2021, 3, 31, 6, 30, 0));

        assertEquals(JobStatus.CANCELLED, updatedJob.getJobStatus());
    }


}
