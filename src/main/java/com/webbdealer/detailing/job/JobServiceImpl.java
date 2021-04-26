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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private CompanyService companyService;

    private JobRepository jobRepository;

    private JobActionService jobActionService;

    private VehicleService vehicleService;

    private VehicleLookupService vehicleLookupService;

    private CustomerService customerService;

    private ReconditionService reconditionService;

    private EmployeeService employeeService;

    private ZoneId zoneId;

    @Autowired
    public JobServiceImpl(CompanyService companyService,
                          JobRepository jobRepository,
                          JobActionService jobActionService,
                          VehicleService vehicleService,
                          VehicleLookupService vehicleLookupService,
                          CustomerService customerService,
                          ReconditionService reconditionService,
                          EmployeeService employeeService) {

        this.companyService = companyService;
        this.jobRepository = jobRepository;
        this.jobActionService = jobActionService;
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
    @Transactional
    public Job startJob(Job job, User user, LocalDateTime startAt) throws InvalidJobStatusException, InvalidJobActionException {
        // 1. Is this user already working this job?
        // if yes, throw exception
        // 2. Is this user working another active job?
        // If yes, stop that job and start this one
        // A user should only be active on one job at a time
        // else, mark this job as active

        if(!isJobPending(job)) {
            throw new InvalidJobStatusException("Job has already been started.");
        }
        else if(hasEmployeeStartedJob(job, user)) {
            throw new InvalidJobActionException("Employee "+user.getUserName()+" has already started this job.");
        }
        else {
            job.setJobStatus(JobStatus.ACTIVE);
            storeJob(user.getCompany().getId(), job);

            // Create a new action for this job
            JobAction startAction = jobActionService.logStartAction(job, user, startAt);

            job.getJobActions().add(startAction); // adds the job action to this job
            job.getAssignedEmployees().add(user); // adds this user to the list of assigned employees

            jobActionService.saveJobAction(startAction);
        }

        return job;
    }

    @Override
    public Job approveJob(Job job, User user, LocalDateTime approvedAt) {
        // ONLY A MANAGER OR QC PERSON CAN APPROVE

        // Job must be AWAITING_APPROVAL
        if(isJobAwaitingApproval(job)) {
            job.setJobStatus(JobStatus.COMPLETED);
            storeJob(user.getCompany().getId(), job);

            JobAction approveAction = jobActionService.logApprovalAction(job, user, approvedAt);
            jobActionService.saveJobAction(approveAction);
        }
        return job;
    }

    @Override
    public Job denyJob(Job job, User user, LocalDateTime deniedAt) {
        // ONLY A MANAGER OR QC PERSON CAN DENY

        if(isJobAwaitingApproval(job)) {
            // leave the job status as AWAITING_APPROVAL
            JobAction denyAction = jobActionService.logDenialAction(job, user, deniedAt);
            jobActionService.saveJobAction(denyAction);
        }

        return job;
    }

    @Override
    public Job markJobAsFinished(Job job, User user, LocalDateTime finishedAt) throws InvalidJobStatusException {
        // Employee marks job as complete. This puts the job in the AWAITING_APPROVAL status
        List<JobAction> jobActions = job.getJobActions();

//        boolean hasEmployeeStartedJob = hasEmployeeStartedJob(jobActions, user.getId());

        if(isJobPending(job)) {
            logger.info("Job has not been started yet.");
            throw new InvalidJobStatusException("Job has not been started yet.");
        }
        else if(isJobCompleted(job)) {
            logger.info("Job has already been completed");
            throw new InvalidJobStatusException(("This job has already been completed."));
        }
        else if(isJobPaused(job)) {
            logger.info("Job is currently paused.");
            throw new InvalidJobStatusException(("Job is currently paused."));
        }
        else if(isJobCancelled(job)) {
            logger.info("Job has been cancelled.");
            throw new InvalidJobStatusException(("Job has been cancelled."));
        }
        else if(numberOfEmployeesOnJob(jobActions) == 1) {
            job.setJobStatus(JobStatus.AWAITING_APPROVAL);
            storeJob(user.getCompany().getId(), job);
        }
        JobAction finishAction = jobActionService.logFinishAction(job, user, finishedAt);
        jobActionService.saveJobAction(finishAction);
        // If yes,
        // 2. Are there any other users on this job?
        // If yes, job stays active
        // If no, mark job as completed

        // DO NOT ALLOW JOB TO STOP IF THERE ARE OTHER USERS WORKING ON IT
        return job;
    }

    @Override
    public Job pauseJob(Job job, User user, LocalDateTime pauseAt) throws InvalidJobStatusException {
        // 1. Has job been started?
        // If yes, job can be paused
        // If no, throw exception (job hasn't been started yet!)
        List<JobAction> jobActions = job.getJobActions();

//        boolean hasEmployeeStartedJob = hasEmployeeStartedJob(jobActions, user.getId());

        if(isJobPending(job)) {
            logger.info("Job hasn't been started yet.");
            throw new InvalidJobStatusException("Job hasn't been started yet.");
        }
        else if(isJobCompleted(job)) {
            logger.info("Job has already been completed");
            throw new InvalidJobStatusException(("This job has already been completed."));
        }
        else if(job.getJobStatus().equals(JobStatus.PENDING)
                || job.getJobStatus().equals(JobStatus.COMPLETED)) {

            logger.info("Job has to be started before it can be paused.");
            throw new InvalidJobStatusException(("Job has to be started before it can be paused."));
        }
        else if(isJobPaused(job)) {
            logger.info("Job is already paused");
            throw new InvalidJobStatusException(("This job is already paused."));
        }
        else if(numberOfEmployeesOnJob(jobActions) == 1) {
            job.setJobStatus(JobStatus.PAUSED);
            storeJob(user.getCompany().getId(), job);

            JobAction jobAction = jobActionService.logPauseAction(job, user, pauseAt);
            jobActionService.saveJobAction(jobAction);
        }
        // When job is paused by admin, the time for all users on this job stops
        return job;
    }

    @Override
    public Job resumeJob(Job job, User user, LocalDateTime resumeAt) throws InvalidJobStatusException {
        // If job is paused, we can resume
        if(isJobPaused(job)) {
            job.setJobStatus(JobStatus.ACTIVE);
            storeJob(user.getCompany().getId(), job);

            JobAction jobAction = jobActionService.logResumeAction(job, user, resumeAt);
            jobActionService.saveJobAction(jobAction);
        }
        // else throw an exception
        else {
            logger.info("Job is not paused, so cannot be resumed.");
            throw new InvalidJobStatusException(("Job is not paused, so cannot be resumed."));
        }
        return job;
    }

    @Override
    public Job cancelJob(Job job, User user, LocalDateTime cancelAt) throws InvalidJobStatusException {
        // ONLY ADMIN OR MANAGER CAN CANCEL JOB
        // Job can be in any state to be cancelled

        // The the employees that are active on this job, then send a PAUSE action
        // any actions on the job must be stopped (paused)
        List<User> assignedEmployees = job.getAssignedEmployees();

        // Pause this employee's progress on this job
        // Need to assign some employees to this job
        assignedEmployees.forEach(employee -> {
            logger.info("Cancelling job for employees assigned: " + employee.getUserName());
            jobActionService.saveJobAction(jobActionService.logPauseAction(job, user, cancelAt));
        });

        JobAction cancelAction = jobActionService.logCancelAction(job, user, cancelAt);
        jobActionService.saveJobAction(cancelAction);

        job.setJobStatus(JobStatus.CANCELLED);
        storeJob(user.getCompany().getId(), job);

        return job;
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

        // New jobs are set to PENDING status
        job.setJobStatus(JobStatus.PENDING);

        return jobRepository.save(job);
    }

    @Override
    public Job storeJob(Long companyId, Job job) {

        Company company = companyService.fetchByIdReference(companyId);

        job.setCompany(company);
        return jobRepository.save(job);
    }

    @Override
    public Job storeJobStatus(Job job) {
        return null;
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

    public boolean isJobPending(Job job) {
        return job.getJobStatus().equals(JobStatus.PENDING);
    }

    public boolean isJobActive(Job job) {
        return job.getJobStatus().equals(JobStatus.ACTIVE);
    }

    public boolean isJobCompleted(Job job) {
        return job.getJobStatus().equals(JobStatus.COMPLETED);
    }

    public boolean isJobPaused(Job job) {
        return job.getJobStatus().equals(JobStatus.PAUSED);
    }

    public boolean isJobAwaitingApproval(Job job) {
        return job.getJobStatus().equals(JobStatus.AWAITING_APPROVAL);
    }

    public boolean isJobCancelled(Job job) {
        return job.getJobStatus().equals(JobStatus.CANCELLED);
    }

    // Not sure if this should be the way to do this.
    public boolean hasEmployeeStartedJob(Job job, User user) {
        List<User> assignedEmployees = job.getAssignedEmployees();

        return job.getJobActions().stream()
                .filter(jobAction -> jobAction.getAction().equals(Action.START))
                .anyMatch(jobAction -> assignedEmployees.contains(jobAction.getUser()));

    }

    public int numberOfEmployeesOnJob(List<JobAction> jobActions) {

        Set<Long> employeeIds = new HashSet<>();
        jobActions.forEach(jobAction -> employeeIds.add(jobAction.getUser().getId()));
        logger.info("Number of employees on this job: " + employeeIds.size());
        return employeeIds.size();
    }

    public List<JobAction[]> filterJobActionByEmployee(User user, List<JobAction[]> jobTimeBlocks) {

        return jobTimeBlocks.stream()
                .filter(action -> action[0].getUser().equals(user))
                .filter(action -> action[1].getUser().equals(user))
                .collect(Collectors.toList());
    }

    public Duration calculateTotalJobTime(List<JobAction[]> jobTimeBlocks) {
        List<Duration> durationList = new ArrayList<>();

        jobTimeBlocks.forEach(jobActionArray -> {
            LocalTime startTime = jobActionArray[0].getJobActionAt().toLocalTime();
            LocalTime stopTime = jobActionArray[1].getJobActionAt().toLocalTime();
            durationList.add(Duration.between(startTime, stopTime));
        });

        logger.info("duration list: " + durationList.toString());

        Duration totalDuration = durationList.stream()
                .reduce(Duration.ZERO, (duration1, duration2) -> duration1.plus(duration2));
        return totalDuration;
    }

    public List<JobAction[]> filterJobActionTimeBlocks(List<JobAction> jobActions) {

        jobActions.sort(Comparator.comparing(JobAction::getJobActionAt));

        List<JobAction[]> durationBlocks = new ArrayList<>();

        JobAction[] startStopArray = new JobAction[2];

        for(JobAction jobAction : jobActions) {
            Action action = jobAction.getAction();
            logger.info("action: " + jobAction.getAction() + " --- actionAt: " + jobAction.getJobActionAt());

            if(action.equals(Action.START) || action.equals(Action.RESUME)) {
//                logger.info("start action: " + action);
                startStopArray[0] = jobAction;
            }
            if(action.equals(Action.PAUSE) || action.equals(Action.FINISH)) {
//                logger.info("stop action: " + action);
                startStopArray[1] = jobAction;

                durationBlocks.add(startStopArray);
                logger.info("start: " + startStopArray[0].getJobActionAt() + " | stop: " + startStopArray[1].getJobActionAt());
                startStopArray = new JobAction[2];
            }
//            logger.info(startStopArray.toString());
        }

        return durationBlocks;
    }
}
