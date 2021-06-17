package com.webbdealer.detailing.customer;

import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dao.CustomerType;
import com.webbdealer.detailing.customer.dto.CustomerCreateForm;
import com.webbdealer.detailing.customer.dto.CustomerResponse;
import com.webbdealer.detailing.job.dao.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> fetchById(Long companyId, Long id);

    Page<Customer> fetchCustomerByType(Long companyId, CustomerType type, Pageable pageable);

    Optional fetchCustomerDetails(Long companyId, Customer customer);

    Customer fetchOrCreateCustomerFromRequest(Long companyId, CustomerCreateForm customerForm);

    Customer attachCustomerToJob(Long customerId, Job job);

    Customer storeCustomerFromRequest(Long customerId, CustomerCreateForm customerCreateForm);

    Customer storeCustomer(Long customerId, Customer customer);

    CustomerResponse mapCustomerToResponse(Customer customer);
}
