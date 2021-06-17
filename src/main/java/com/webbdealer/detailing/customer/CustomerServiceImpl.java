package com.webbdealer.detailing.customer;

import com.webbdealer.detailing.company.CompanyService;
import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dao.CustomerRepository;
import com.webbdealer.detailing.customer.dao.CustomerType;
import com.webbdealer.detailing.customer.dto.CustomerCreateForm;
import com.webbdealer.detailing.customer.dto.CustomerResponse;
import com.webbdealer.detailing.job.dao.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<Customer> fetchById(Long companyId, Long id) {
        return customerRepository.findByCompanyIdAndId(companyId, id);
    }

    @Override
    public Page<Customer> fetchCustomerByType(Long companyId, CustomerType type, Pageable pageable) {
        Page<Customer> pagedCustomerList = customerRepository.findByCompanyIdAndCustomerType(companyId, type, pageable);
        return new PageImpl<>(pagedCustomerList.toList(), pageable, pagedCustomerList.getTotalElements());
    }

    @Override
    public Optional fetchCustomerDetails(Long companyId, Customer customer) {
        return Optional.empty();
    }

    @Override
    public Customer fetchOrCreateCustomerFromRequest(Long companyId, CustomerCreateForm customerForm) {
        final String firstName = customerForm.getFirstName();
        final String lastName = customerForm.getLastName();
        final String phone = customerForm.getPhone();
        Optional<Customer> optionalCustomer
                = customerRepository.findByFirstNameAndLastNameAndPhone(firstName, lastName, phone);

        return optionalCustomer.orElseGet(() -> storeCustomerFromRequest(companyId, customerForm));
    }

    @Override
    public Customer attachCustomerToJob(Long customerId, Job job) {

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Customer customer = optionalCustomer.orElseThrow();
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
        customer.setNotes(customerCreateForm.getNotes());
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
