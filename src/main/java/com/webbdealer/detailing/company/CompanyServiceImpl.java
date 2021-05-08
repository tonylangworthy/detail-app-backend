package com.webbdealer.detailing.company;

import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.company.dao.CompanyRepository;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.job.dao.Job;
import com.webbdealer.detailing.recondition.dao.Recondition;
import com.webbdealer.detailing.security.AuthorityService;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    private AuthorityService authorityService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, AuthorityService authorityService) {
        this.companyRepository = companyRepository;
        this.authorityService = authorityService;
    }

    @Override
    public boolean companyExists(String email, String phone) {
        return  companyRepository.existsByEmailAndPhoneIgnoreCase(email, phone);
    }

    @Override
    public Company createCompany(Company company) {
        if(companyExists(company.getEmail(), company.getPhone())) {
            throw new CompanyExistsException("A company with this email and phone number already exists");
        }
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> fetchById(Long id) {

        return companyRepository.findById(id);


    }

    @Override
    public Company fetchByIdReference(Long id) {
        return companyRepository.getOne(id);
    }

    @Override
    public Company attachCustomerToCompany(Customer customer, Long companyId) {
        Company company = companyRepository.getOne(companyId);
        customer.setCompany(company);
        return company;
    }

    @Override
    public Company attachVehicleToCompany(Vehicle vehicle, Long companyId) {
        Company company = companyRepository.getOne(companyId);
        vehicle.setCompany(company);
        return company;
    }

    @Override
    public Company attachReconServiceToCompany(Recondition recondition, Long companyId) {
        Company company = companyRepository.getOne(companyId);
        recondition.setCompany(company);
        return company;
    }

    @Override
    public Company attachJobToCompany(Job job, Long companyId) {
        Company company = companyRepository.getOne(companyId);
        job.setCompany(company);
        return company;
    }

    @Override
    public ZoneId companyTimeZone(Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        Company company = optionalCompany.orElseThrow(
                () -> new EntityNotFoundException("Company with id of ["+companyId+"] not found!"));
        String companyTimezone = company.getTimezone();
        return ZoneId.of(companyTimezone);
    }


}
