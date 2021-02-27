package com.webbdealer.detailing.customer;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.company.dao.Company;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dao.CustomerRepository;
import com.webbdealer.detailing.customer.dao.CustomerType;
import com.webbdealer.detailing.customer.dto.CustomerCreateForm;
import com.webbdealer.detailing.customer.dto.CustomerResponse;
import com.webbdealer.detailing.job.dao.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private CompanyService companyService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CompanyService companyService) {
        this.customerRepository = customerRepository;
        this.companyService = companyService;
    }

    @Override
    public Customer attachCustomerToJob(Long customerId, Job job) {

        Customer customer = customerRepository.getOne(customerId);
        job.setCustomer(customer);
        return customer;
    }

    @Override
    public Customer storeCustomerFromRequest(Long companyId, CustomerCreateForm customerCreateForm) {

        Customer customer = new Customer();
        customer.setCustomerType(customerCreateForm.getCustomerType());
        customer.setFirstName(customerCreateForm.getFirstName());
        customer.setLastName(customerCreateForm.getLastName());
        customer.setBusiness(customerCreateForm.getBusiness());
        customer.setEmail(customerCreateForm.getEmail());
        customer.setPhone(customerCreateForm.getPhone());
        customer.setReceiveTexts(customerCreateForm.isReceiveTexts());
        return storeCustomer(companyId, customer);
    }

    @Override
    public Customer storeCustomer(Long companyId, Customer customer) {
        companyService.attachCustomerToCompany(customer, companyId);
        return customerRepository.save(customer);
    }

    @Override
    public CustomerResponse mapCustomerToResponse(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setFirstName(customer.getFirstName());
        customerResponse.setLastName(customer.getLastName());
        customerResponse.setPhone(customer.getPhone());
        customerResponse.setReceiveTexts(customer.isReceiveTexts());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setNotes(customer.getNotes());
        customerResponse.setCustomerType(customer.getCustomerType());
        customerResponse.setCreatedAt(customer.getCreatedAt().toString());
//        customerResponse.setUpdatedAt(customer.getUpdatedAt().toString());
        return customerResponse;
    }
}
