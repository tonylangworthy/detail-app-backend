package com.webbdealer.detailing.job;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.customer.CustomerService;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dto.CustomerResponse;
import com.webbdealer.detailing.employee.EmployeeService;
import com.webbdealer.detailing.employee.dao.User;
import com.webbdealer.detailing.employee.dto.EmployeeResponse;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.job.dao.JobRepository;
import com.webbdealer.detailing.job.dao.JobStatus;
import com.webbdealer.detailing.job.dto.JobCreateForm;
import com.webbdealer.detailing.job.dto.JobResponse;
import com.webbdealer.detailing.recondition.ReconditionService;
import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.recondition.dto.ReconditionCreateForm;
import com.webbdealer.detailing.recondition.dto.ReconditionServiceResponse;
import com.webbdealer.detailing.vehicle.VehicleService;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import com.webbdealer.detailing.vehicle.dto.VehicleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private CompanyService companyService;

    private JobRepository jobRepository;

    private VehicleService vehicleService;

    private CustomerService customerService;

    private ReconditionService reconditionService;

    private EmployeeService employeeService;

    @Autowired
    public JobServiceImpl(CompanyService companyService,
                          JobRepository jobRepository,
                          VehicleService vehicleService,
                          CustomerService customerService,
                          ReconditionService reconditionService,
                          EmployeeService employeeService) {

        this.companyService = companyService;
        this.jobRepository = jobRepository;
        this.vehicleService = vehicleService;
        this.customerService = customerService;
        this.reconditionService = reconditionService;
        this.employeeService = employeeService;
    }

    @Override
    public Job fetchById(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        return optionalJob.orElseThrow(() -> new EntityNotFoundException("Job with id of ["+id+"] not found!"));
    }

    @Override
    @Transactional
    public List<JobResponse> fetchAllJobs(Long companyId) {

        List<Job> jobs = jobRepository.findAllByCompanyId(companyId);
        return mapJobListToResponseList(jobs);
    }

    @Override
    public List<JobResponse> fetchPendingJobs(Long companyId) {
        return null;
    }

    @Override
    public List<JobResponse> fetchActiveJobs(Long companyId) {
        return null;
    }

    @Override
    public List<JobResponse> fetchCompletedJobs(Long companyId) {
        return null;
    }

    @Override
    @Transactional
    public Job storeJobFromRequest(Long companyId, JobCreateForm jobCreateForm) {

        Vehicle vehicle = vehicleService.storeVehicleFromRequest(companyId, jobCreateForm.getVehicle());
        Customer customer = customerService.storeCustomerFromRequest(companyId, jobCreateForm.getCustomer());

        Job job = new Job();
        job.setVehicle(vehicle);
        job.setCustomer(customer);
//        job.setJobStartedAt(LocalDateTime.now());
        job.setManagerNotes(jobCreateForm.getManagerNotes());

        reconditionService.attachReconServicesToJob(jobCreateForm.getServiceIds(), job);
        companyService.attachJobToCompany(job, companyId);

        return jobRepository.save(job);
    }

    @Override
    public Job storeJob(Long companyId, JobCreateForm jobCreateForm) {

        Job job = new Job();

        // This can only be created by a manager
        // 1. Store vehicle

        // 2. Store customer, if customer doesn't exist
        // otherwise attach customer to this job

        // 3. Attach services to this job

        Company company = companyService.attachJobToCompany(job, companyId);

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
    public void startJob(Long id) {
        Job job = fetchById(id);
        job.setJobStartedAt(LocalDateTime.now());
    }

    @Override
    public void pauseJob(Long id) {
        Job job = fetchById(id);
        job.setJobPausedAt(LocalDateTime.now());
    }

    @Override
    public void endJob(Long id) {
        Job job = fetchById(id);
        job.setJobEndedAt(LocalDateTime.now());
    }

    @Override
    public void cancelJob(Long id) {
        Job job = fetchById(id);
        job.setJobCanceled(true);
    }

    private List<JobResponse> mapJobListToResponseList(List<Job> jobs) {
        List<JobResponse> jobResponseList = new ArrayList<>();

        jobs.forEach(job -> {
            jobResponseList.add(mapJobToResponse(job));
        });
        return jobResponseList;
    }

    private JobResponse mapJobToResponse(Job job) {
        JobResponse jobResponse = new JobResponse();

        CustomerResponse customerResponse = customerService.mapCustomerToResponse(job.getCustomer());
        jobResponse.setCustomer(customerResponse);

        VehicleResponse vehicleResponse = vehicleService.mapVehicleToResponse(job.getVehicle());
        jobResponse.setVehicle(vehicleResponse);

        List<EmployeeResponse> employeeList = employeeService.mapEmployeeListToResponseList(job.getEmployees());
        jobResponse.setEmployees(employeeList);

        List<ReconditionServiceResponse> reconditionList = reconditionService.mapReconditionListToResponseList(job.getReconditioningServices());
        jobResponse.setServices(reconditionList);

        jobResponse.setStatus(jobStatus(job));
        jobResponse.setEmployeeNotes(job.getEmployeeNotes());
        jobResponse.setManagerNotes(job.getManagerNotes());
        jobResponse.setCreatedAt(job.getCreatedAt());
        jobResponse.setUpdatedAt(job.getUpdatedAt());


        return jobResponse;
    }
}
