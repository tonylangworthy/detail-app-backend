package com.webbdealer.detailing.job.dao;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.customer.CustomerService;
import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.job.JobService;
import com.webbdealer.detailing.job.JobServiceImpl;
import com.webbdealer.detailing.recondition.ReconditionService;
import com.webbdealer.detailing.vehicle.VehicleLookupService;
import com.webbdealer.detailing.vehicle.VehicleService;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JobServiceImplIntegrationTests {

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


    @InjectMocks
    private JobService jobService = new JobServiceImpl(
            companyService,
            jobRepository,
            vehicleService,
            vehicleLookupService,
            customerService,
            reconditionService,
            employeeService);

    @BeforeEach
    void setMockOutput() {
        LocalDateTime startAt = LocalDateTime.of(2021, 03, 14, 7, 51);

    }
}
