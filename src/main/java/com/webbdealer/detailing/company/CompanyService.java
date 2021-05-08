package com.webbdealer.detailing.company;

import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.vehicle.dao.Vehicle;

import java.time.ZoneId;
import java.util.Optional;

public interface CompanyService {

    boolean companyExists(String email, String phone);

    Company createCompany(Company company);

    Optional<Company> fetchById(Long id);

    Company fetchByIdReference(Long id);

    Company attachCustomerToCompany(Customer customer, Long companyId);

    Company attachVehicleToCompany(Vehicle vehicle, Long companyId);

    Company attachReconServiceToCompany(Recondition recondition, Long companyId);

    Company attachJobToCompany(Job job, Long companyId);

    ZoneId companyTimeZone(Long companyId);
}
