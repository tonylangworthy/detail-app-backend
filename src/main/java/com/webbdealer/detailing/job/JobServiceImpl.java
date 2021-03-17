package com.webbdealer.detailing.job;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.customer.CustomerService;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dto.CustomerCreateForm;
import com.webbdealer.detailing.customer.dto.CustomerResponse;
import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobRepository;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.job.dto.CreateJobRequest;
import com.webbdealer.detailing.job.dto.JobItemResponse;
import com.webbdealer.detailing.job.dto.JobDetailsResponse;
import com.webbdealer.detailing.recondition.ReconditionService;
import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.recondition.dto.ReconditionServiceResponse;
import com.webbdealer.detailing.shared.TimezoneConverter;
import com.webbdealer.detailing.vehicle.VehicleLookupService;
import com.webbdealer.detailing.vehicle.VehicleService;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import com.webbdealer.detailing.vehicle.dto.VehicleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private CompanyService companyService;

    private JobRepository jobRepository;

    private VehicleService vehicleService;

    private VehicleLookupService vehicleLookupService;

    private CustomerService customerService;

    private ReconditionService reconditionService;

    private EmployeeService employeeService;

    private ZoneId zoneId;

    @Autowired
    public JobServiceImpl(CompanyService companyService,
                          JobRepository jobRepository,
                          VehicleService vehicleService,
                          VehicleLookupService vehicleLookupService,
                          CustomerService customerService,
                          ReconditionService reconditionService,
                          EmployeeService employeeService) {

        this.companyService = companyService;
        this.jobRepository = jobRepository;
        this.vehicleService = vehicleService;
        this.vehicleLookupService = vehicleLookupService;
        this.customerService = customerService;
        this.reconditionService = reconditionService;
        this.employeeService = employeeService;

        this.zoneId = ZoneId.of("America/Chicago");

    }

    @Override
    public Job fetchById(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        return optionalJob.orElseThrow(() -> new EntityNotFoundException("Job with id of ["+id+"] not found!"));
    }

    @Override
    public Job fetchByIdReference(Long id) {
        return jobRepository.getOne(id);
    }

    @Override
    @Transactional
    public List<JobItemResponse> fetchAllJobs(Long companyId) {

        List<Job> jobs = jobRepository.findAllByCompanyId(companyId);
        return mapJobListToResponseList(companyId, jobs);
    }

    @Override
    public List<JobItemResponse> fetchPendingJobs(Long companyId) {
        return null;
    }

    @Override
    public List<JobItemResponse> fetchActiveJobs(Long companyId) {
        return null;
    }

    @Override
    public List<JobItemResponse> fetchCompletedJobs(Long companyId) {
        return null;
    }

    @Override
    public JobDetailsResponse fetchJobDetails(Long companyId, Long jobId) {
        Optional<Job> optionalJob = jobRepository.findByCompanyIdAndId(companyId, jobId);
        Job job = optionalJob.orElseThrow(() -> new EntityNotFoundException("Job with id of ["+jobId+"] not found!"));
        return mapJobToJobDetailsResponse(companyId, job);
    }

    @Override
    public Duration jobTotalTime(Long jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        Job job = optionalJob.orElseThrow(() -> new EntityNotFoundException("Job with id of ["+jobId+"] not found!"));

        Optional<LocalDateTime> optionalStartedAt = Optional.empty();
        Optional<LocalDateTime> optionalPausedAt = Optional.empty();
        Optional<LocalDateTime> optionalEndedAt = Optional.empty();

        if(Objects.nonNull(job.getJobStartedAt())) optionalStartedAt = Optional.of(job.getJobStartedAt().toLocalDateTime());
        if(Objects.nonNull(job.getJobPausedAt())) optionalPausedAt = Optional.of(job.getJobPausedAt().toLocalDateTime());
        if(Objects.nonNull(job.getJobEndedAt())) optionalEndedAt = Optional.of(job.getJobEndedAt().toLocalDateTime());

        LocalDateTime startedAt = optionalStartedAt.orElseThrow();
//        LocalDateTime pausedAt = optionalPausedAt.orElseThrow();
        LocalDateTime endedAt = optionalEndedAt.orElseThrow();

        long diff = ChronoUnit.MINUTES.between(startedAt, endedAt);
        logger.info("difference in minutes: " + diff);
        return Duration.ZERO;
    }

    @Override
    @Transactional
    public Job storeJobFromRequest(Long companyId, CreateJobRequest createJobRequest) {

        Job job = new Job();

        // This can only be created by a manager
        // 1. Store vehicle
        Long vehicleId = createJobRequest.getVehicle().getId();
        Vehicle vehicle;
        if(vehicleId == null) {
            vehicle = vehicleService.fetchOrCreateVehicleFromRequest(companyId, createJobRequest.getVehicle());
        }
        else {
            vehicle = vehicleService.fetchByIdReference(vehicleId);
        }
        job.setVehicle(vehicle);


        // 2. Store customer, if customer doesn't exist
        // otherwise attach customer to this job
        // If customerId == null, then create a new customer
        CustomerCreateForm customerForm = createJobRequest.getCustomer();
        Long customerId = customerForm.getId();

        Customer customer;
        if(customerId == null) {
            customer = customerService.fetchOrCreateCustomerFromRequest(companyId, customerForm);
        }
        else {
            customer = customerService.fetchByIdReference(customerId);
        }
        job.setCustomer(customer);

        // 3. Attach services to this job
        List<Long> serviceIdsList = createJobRequest.getServiceIds();
        serviceIdsList.forEach(id -> {
            Recondition recondition = reconditionService.fetchByIdReference(id);
            recondition.getJobs().add(job);
        });

        Company company = companyService.attachJobToCompany(job, companyId);



//        job.setJobStartedAt(LocalDateTime.now());
        String managerNotes = createJobRequest.getManagerNotes();
        logger.info(managerNotes);
        job.setManagerNotes(managerNotes);

        reconditionService.attachReconServicesToJob(createJobRequest.getServiceIds(), job);
        companyService.attachJobToCompany(job, companyId);

        return jobRepository.save(job);
    }

    @Override
    public Job storeJob(Long companyId, Job job) {

        Company company = companyService.fetchByIdReference(companyId);

        job.setCompany(company);
        return jobRepository.save(job);
    }

    @Override
    public JobStatus jobStatus(Job job) {

        JobStatus status = null;

        // If the job_started_at is null, the status is PENDING
        if (job.getJobStartedAt() == null) {
            logger.info("JOB IS PENDING");
            status = JobStatus.PENDING;
        }

        // If job_started_at is before now and job_ended_at == null, and job_paused_at is null, the job is ACTIVE
        else if (job.getJobStartedAt() != null && job.getJobEndedAt() == null) {
            logger.info("JOB IS ACTIVE");
            status = JobStatus.ACTIVE;
        }

        // If job_finished === true, the status is COMPLETED
        else if (job.getJobEndedAt() != null) {
            logger.info("JOB IS COMPLETED");
            status = JobStatus.COMPLETED;
        }
        return status;
    }

    @Override
    public Job startJob(Long jobId, Long userId, LocalDateTime startAt) {
        Job job = fetchById(jobId);
        User user = employeeService.fetchByIdReference(userId);

        if(job.getJobEndedAt() != null) {
            throw new InvalidJobActionException("Job already ended!!");
        }
        job.setJobStartedAt(startAt.atZone(zoneId));
        job.getEmployees().add(user);
        return jobRepository.save(job);
    }

    @Override
    public Job pauseJob(Long jobId, LocalDateTime pauseAt) {
        Job job = fetchById(jobId);
        if(job.getJobStartedAt() == null) {
            throw new InvalidJobActionException("Job hasn't even been started yet!!");
        }
        if(job.getJobEndedAt() != null) {
            throw new InvalidJobActionException("Job already ended!!");
        }
        job.setJobPausedAt(pauseAt.atZone(zoneId));
        return jobRepository.save(job);
    }

    @Override
    public Job endJob(Long jobId, LocalDateTime endAt) {
        Job job = fetchById(jobId);
        if(job.getJobEndedAt() != null) {
            throw new InvalidJobActionException("Job already ended!!");
        }
        if(job.getJobStartedAt() == null) {
            throw new InvalidJobActionException("Job hasn't even been started yet!!");
        }
        job.setJobEndedAt(endAt.atZone(zoneId));
        return jobRepository.save(job);
    }

    @Override
    public Job cancelJob(Long jobId) {
        Job job = fetchById(jobId);
        job.setJobCanceled(true);
        return jobRepository.save(job);
    }

    private List<JobItemResponse> mapJobListToResponseList(Long companyId, List<Job> jobs) {
        List<JobItemResponse> jobItemResponseList = new ArrayList<>();
        List<Long> catalogIdList = new ArrayList<>();
        jobs.forEach(job -> catalogIdList.add(job.getVehicle().getCatalogId()));

        List<VehicleResponse> vehicleResponseList = vehicleService.fetchVehiclesByCatalogIdList(companyId, catalogIdList);

        jobs.forEach(job -> {
            Optional<VehicleResponse> vehicleResponseOptional = vehicleResponseList.stream()
                    .filter(vehicleResponse -> job.getVehicle().getCatalogId().equals(vehicleResponse.getCatalogId()))
                    .findFirst();
                jobItemResponseList.add(mapJobToJobItemResponse(job, vehicleResponseOptional));
        });

        return jobItemResponseList;
    }

    private JobItemResponse mapJobToJobItemResponse(Job job, Optional<VehicleResponse> optionalVehicleResponse) {
        JobItemResponse jobItem = new JobItemResponse();
        jobItem.setId(job.getId());
        jobItem.setStatus(jobStatus(job));

        Customer customer = job.getCustomer();
        jobItem.setCustomerType(customer.getCustomerType());
        jobItem.setCustomerFirstName(customer.getFirstName());
        jobItem.setCustomerLastName(customer.getLastName());
        jobItem.setCustomerBusiness(customer.getBusiness());

        if(optionalVehicleResponse.isPresent()) {
            VehicleResponse vehicle = optionalVehicleResponse.get();
            jobItem.setVehicleYear(vehicle.getYear());
            jobItem.setVehicleMake(vehicle.getMake());
            jobItem.setVehicleModel(vehicle.getModel());
            jobItem.setVehicleTrim(vehicle.getTrim());
        }

        List<User> employees = job.getEmployees();
        employees.forEach(employee -> {
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            jobItem.getAssignedEmployees().add(firstName + " " + lastName);
        });
        return jobItem;
    }

    private JobDetailsResponse mapJobToJobDetailsResponse(Long companyId, Job job) {
        TimezoneConverter timezoneConverter = new TimezoneConverter.TimezoneConverterBuilder("America/Chicago").build();

        JobDetailsResponse jobDetailsResponse = new JobDetailsResponse();

        CustomerResponse customerResponse = customerService.mapCustomerToResponse(job.getCustomer());
        jobDetailsResponse.setCustomer(customerResponse);

        VehicleResponse vehicleResponse = vehicleService.mapVehicleToResponse(companyId, job.getVehicle());
        jobDetailsResponse.setVehicle(vehicleResponse);

        List<EmployeeResponse> employeeList = employeeService.mapEmployeeListToResponseList(job.getEmployees());
        jobDetailsResponse.setEmployees(employeeList);

        List<ReconditionServiceResponse> reconditionList = reconditionService.mapReconditionListToResponseList(job.getReconditioningServices());
        jobDetailsResponse.setServices(reconditionList);

        jobDetailsResponse.setId(job.getId());
        jobDetailsResponse.setStatus(jobStatus(job));
        jobDetailsResponse.setEmployeeNotes(job.getEmployeeNotes());
        jobDetailsResponse.setManagerNotes(job.getManagerNotes());

        if(job.getJobStartedAt() != null) jobDetailsResponse.setJobStartedAt(timezoneConverter.fromUtcToLocalDateTime(job.getJobStartedAt()));
        if(job.getJobPausedAt() != null) jobDetailsResponse.setJobPausedAt(job.getJobPausedAt().withZoneSameInstant(zoneId).toLocalDateTime());
        if(job.getJobEndedAt() != null) jobDetailsResponse.setJobEndedAt(job.getJobEndedAt().withZoneSameInstant(zoneId).toLocalDateTime());

        jobDetailsResponse.setCreatedAt(job.getCreatedAt());
        jobDetailsResponse.setUpdatedAt(job.getUpdatedAt());


        return jobDetailsResponse;
    }
}
