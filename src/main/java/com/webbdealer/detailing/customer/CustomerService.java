package com.webbdealer.detailing.customer;

import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dto.CustomerCreateForm;
import com.webbdealer.detailing.customer.dto.CustomerResponse;
import com.webbdealer.detailing.job.dao.Job;

public interface CustomerService {

    Customer attachCustomerToJob(Long customerId, Job job);

    Customer storeCustomerFromRequest(Long customerId, CustomerCreateForm customerCreateForm);

    Customer storeCustomer(Long customerId, Customer customer);

    CustomerResponse mapCustomerToResponse(Customer customer);
}
