package com.webbdealer.detailing.customer;

import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dto.CustomerCreateForm;
import com.webbdealer.detailing.customer.dto.CustomerResponse;
import com.webbdealer.detailing.job.dao.Job;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> fetchById(Long id);

    Customer fetchByIdReference(Long id);

    Customer fetchOrCreateCustomerFromRequest(Long companyId, CustomerCreateForm customerForm);

    Customer attachCustomerToJob(Long customerId, Job job);

    Customer storeCustomerFromRequest(Long customerId, CustomerCreateForm customerCreateForm);

    Customer storeCustomer(Long customerId, Customer customer);

    CustomerResponse mapCustomerToResponse(Customer customer);
}
