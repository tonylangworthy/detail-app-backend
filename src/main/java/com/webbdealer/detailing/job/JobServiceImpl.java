package com.webbdealer.detailing.job;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.customer.CustomerService;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dto.CustomerCreateForm;
import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.job.dao.*;
import com.webbdealer.detailing.job.dto.CreateJobRequest;
import com.webbdealer.detailing.job.dto.JobItemResponse;
import com.webbdealer.detailing.job.dto.JobDetailsResponse;
import com.webbdealer.detailing.recondition.ReconditionService;
import com.webbdealer.detailing.recondition.dao.Recondition;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    public Job fetchById(Long companyId, Long id) {
        Optional<Job> optionalJob = jobRepository.findByCompanyIdAndId(companyId, id);
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
    public List<JobItemResponse> fetchJobsByStatus(Long companyId, JobStatus jobStatus) {
        return null;
    }

    @Override
    public JobDetailsResponse fetchJobDetails(Long companyId, Job job) {
        return mapJobToJobDetailsResponse(companyId, job);
    }

    @Override
    public Job startJob(Job job, User user, LocalDateTime startAt) throws InvalidJobActionException {
        // 1. Is this user already working this job?
        // if yes, throw exception
        // 2. Is this user working another active job?
        // If yes, do something about it!
        // A user should only be active on one job at a time
        // else, mark this job as active

        List<JobAction> jobActions = job.getJobActions();
        logger.info(jobActions.toString());

        // This should never happen since the stopJob method marks the job as COMPLETED
        if(hasJobEnded(jobActions)) {
            logger.info("Job has already been completed");
            throw new InvalidJobActionException(("This job has already been completed."));
        }


        if(hasEmployeeStartedJob(jobActions, user.getId())) {
            logger.info("This employee has already started this job.");
            throw new InvalidJobActionException(("This employee has already started this job."));
        }
        else {
            job.getJobActions().add(new JobAction(startAt, Action.START, job, user));
            job.setJobStatus(JobStatus.ACTIVE);
        }
        return job;
    }

    @Override
    public Job stopJob(Job job, User user, LocalDateTime stopAt) throws InvalidJobActionException {
        // 1. Has job been started?
        // If no, throw exception (job hasn't been started yet!
        List<JobAction> jobActions = job.getJobActions();

        boolean hasEmployeeStartedJob = hasEmployeeStartedJob(jobActions, user.getId());

        if(!hasEmployeeStartedJob) {
            logger.info("Job hasn't been started yet.");
            throw new InvalidJobActionException("Job hasn't been started yet.");
        }
        else if(hasJobEnded(jobActions)) {
            logger.info("Job has already been completed");
            throw new InvalidJobActionException(("This job has already been completed."));
        }
        else if(job.getJobStatus().equals(JobStatus.PENDING)
                || job.getJobStatus().equals(JobStatus.COMPLETED)
                || job.getJobStatus().equals(JobStatus.PAUSED)) {

            logger.info("Job cannot be stopped");
            throw new InvalidJobActionException(("This job cannot be stopped at this time."));
        }
        else if(numberOfEmployeesOnJob(jobActions) == 1) {
            job.setJobStatus(JobStatus.COMPLETED);
        }
        job.getJobActions().add(new JobAction(stopAt, Action.STOP, job, user));
        // If yes,
        // 2. Are there any other users on this job?
        // If yes, job stays active
        // If no, mark job as completed

        // DO NOT ALLOW JOB TO STOP IF THERE ARE OTHER USERS WORKING ON IT
        return job;
    }

    @Override
    public Job pauseJob(Job job, User user, LocalDateTime pauseAt) {
        // 1. Has job been started?
        // If yes, job can be paused
        // If no, throw exception (job hasn't been started yet!)
        List<JobAction> jobActions = job.getJobActions();

        boolean hasEmployeeStartedJob = hasEmployeeStartedJob(jobActions, user.getId());

        if(!hasEmployeeStartedJob) {
            logger.info("Job hasn't been started yet.");
            throw new InvalidJobActionException("Job hasn't been started yet.");
        }
        else if(hasJobEnded(jobActions)) {
            logger.info("Job has already been completed");
            throw new InvalidJobActionException(("This job has already been completed."));
        }
        else if(job.getJobStatus().equals(JobStatus.PENDING)
                || job.getJobStatus().equals(JobStatus.COMPLETED)) {

            logger.info("Job cannot be paused");
            throw new InvalidJobActionException(("This job cannot be stopped at this time."));
        }
        else if(job.getJobStatus().equals(JobStatus.PAUSED) || isJobPaused(jobActions)) {
            logger.info("Job is already paused");
            throw new InvalidJobActionException(("This job is already paused."));
        }
        else if(numberOfEmployeesOnJob(jobActions) == 1) {
            job.setJobStatus(JobStatus.PAUSED);
            // Store which job will be paused, which user paused it, and what time it was paused
            job.getJobActions().add(new JobAction(pauseAt, Action.PAUSE, job, user));
        }
        // When job is paused by admin, the time for all users on this job stops
        return job;
    }

    @Override
    public Job resumeJob(Job job, User user, LocalDateTime resumeAt) {
        // 1. Has job been started?
        // If yes, has job been paused?
        // If yes, then job can be resumed
        // If no, throw exception (cannot resume this job)
        if(job.getJobStatus().equals(JobStatus.PENDING) || )

        //
        return null;
    }

    @Override
    public Job cancelJob(Job job, User user, LocalDateTime cancelAt) {
        // ONLY ADMIN OR MANAGER CAN CANCEL JOB
        return null;
    }

    @Override
    public Job addEmployeeToJob(Job job, User user, LocalDateTime startAt) {
        // Add employee to job.
        // If job is currently pending, mark job as active
        return null;
    }

    @Override
    public Job removeEmployeeFromJob(Job job, User user, LocalDateTime stopAt) {
        // Remove employee from job.
        // If job is active, and there are no other employees working the job
        // set the job status to paused

        // If other users are working the job, keep status as active
        return null;
    }

    @Override
    public Duration jobTotalTime(Job job) {
        // This calculates the total man hours for this job
        // If 2 employees spent 4 hours each, total time would be 8 hours

        Optional<LocalDateTime> optionalStartedAt = Optional.empty();
        Optional<LocalDateTime> optionalPausedAt = Optional.empty();
        Optional<LocalDateTime> optionalEndedAt = Optional.empty();

//        if(Objects.nonNull(job.getJobStartedAt())) optionalStartedAt = Optional.of(job.getJobStartedAt().toLocalDateTime());
//        if(Objects.nonNull(job.getJobPausedAt())) optionalPausedAt = Optional.of(job.getJobPausedAt().toLocalDateTime());
//        if(Objects.nonNull(job.getJobEndedAt())) optionalEndedAt = Optional.of(job.getJobEndedAt().toLocalDateTime());

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
//        jobItem.setId(job.getId());
//        jobItem.setStatus(jobStatus(job));
//
//        Customer customer = job.getCustomer();
//        jobItem.setCustomerType(customer.getCustomerType());
//        jobItem.setCustomerFirstName(customer.getFirstName());
//        jobItem.setCustomerLastName(customer.getLastName());
//        jobItem.setCustomerBusiness(customer.getBusiness());
//
//        if(optionalVehicleResponse.isPresent()) {
//            VehicleResponse vehicle = optionalVehicleResponse.get();
//            jobItem.setVehicleYear(vehicle.getYear());
//            jobItem.setVehicleMake(vehicle.getMake());
//            jobItem.setVehicleModel(vehicle.getModel());
//            jobItem.setVehicleTrim(vehicle.getTrim());
//        }
//
//        List<User> employees = job.getEmployees();
//        employees.forEach(employee -> {
//            String firstName = employee.getFirstName();
//            String lastName = employee.getLastName();
//            jobItem.getAssignedEmployees().add(firstName + " " + lastName);
//        });
        return jobItem;
    }

    private JobDetailsResponse mapJobToJobDetailsResponse(Long companyId, Job job) {
        TimezoneConverter timezoneConverter = new TimezoneConverter.TimezoneConverterBuilder("America/Chicago").build();

        JobDetailsResponse jobDetailsResponse = new JobDetailsResponse();

//        CustomerResponse customerResponse = customerService.mapCustomerToResponse(job.getCustomer());
//        jobDetailsResponse.setCustomer(customerResponse);
//
//        VehicleResponse vehicleResponse = vehicleService.mapVehicleToResponse(companyId, job.getVehicle());
//        jobDetailsResponse.setVehicle(vehicleResponse);
//
//        List<EmployeeResponse> employeeList = employeeService.mapEmployeeListToResponseList(job.getEmployees());
//        jobDetailsResponse.setEmployees(employeeList);
//
//        List<ReconditionServiceResponse> reconditionList = reconditionService.mapReconditionListToResponseList(job.getReconditioningServices());
//        jobDetailsResponse.setServices(reconditionList);
//
//        jobDetailsResponse.setId(job.getId());
//        jobDetailsResponse.setStatus(jobStatus(job));
//        jobDetailsResponse.setEmployeeNotes(job.getEmployeeNotes());
//        jobDetailsResponse.setManagerNotes(job.getManagerNotes());

//        if(job.getJobStartedAt() != null) jobDetailsResponse.setJobStartedAt(timezoneConverter.fromUtcToLocalDateTime(job.getJobStartedAt()));
//        if(job.getJobPausedAt() != null) jobDetailsResponse.setJobPausedAt(job.getJobPausedAt().withZoneSameInstant(zoneId).toLocalDateTime());
//        if(job.getJobEndedAt() != null) jobDetailsResponse.setJobEndedAt(job.getJobEndedAt().withZoneSameInstant(zoneId).toLocalDateTime());

        jobDetailsResponse.setCreatedAt(job.getCreatedAt());
        jobDetailsResponse.setUpdatedAt(job.getUpdatedAt());


        return jobDetailsResponse;
    }

    public boolean hasJobBeenStarted(List<JobAction> jobActions) {

    }

    public boolean hasJobEnded(List<JobAction> jobActions) {
        // If any actions for this job equal STOP, this job has ended
        return jobActions.stream()
                .anyMatch(jobAction -> jobAction.getAction().equals(Action.STOP));
    }

    // Not sure if this should be the way to do this.
    public boolean hasEmployeeStartedJob(List<JobAction> jobActions, Long userId) {

        logger.info("jobActions size: " + jobActions.size());
        if(jobActions.size() == 0) return false;

        return jobActions.stream()
                .anyMatch(jobAction -> {
                    logger.info("UserID: " + jobAction.getUser().getId());
                    if(jobAction.getAction().equals(Action.START) || jobAction.getAction().equals(Action.RESUME)) {
                        return jobAction.getUser().getId().equals(userId);
                    }
                    return false;
                });
    }

    public boolean isJobPaused(List<JobAction> jobActions) {
//        List<JobAction> filteredActions = new ArrayList<>();
        jobActions.sort(Comparator.comparing(JobAction::getJobActionAt));
        jobActions.forEach(jobAction -> logger.info("action at: " + jobAction.getJobActionAt()));

        JobAction lastAction = jobActions.get(jobActions.size()-1);
        // If the last action is RESUME, it can be PAUSED
        if(lastAction.getAction().equals(Action.RESUME)) return false;
        return true;
    }

    public int numberOfEmployeesOnJob(List<JobAction> jobActions) {

        Set<Long> employeeIds = new HashSet<>();
        jobActions.forEach(jobAction -> employeeIds.add(jobAction.getUser().getId()));
        logger.info("Number of employees on this job: " + employeeIds.size());
        return employeeIds.size();
    }
}
