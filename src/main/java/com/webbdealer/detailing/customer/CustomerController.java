package com.webbdealer.detailing.customer;

import com.webbdealer.detailing.customer.dao.Customer;
import com.webbdealer.detailing.customer.dao.CustomerType;
import com.webbdealer.detailing.customer.dto.CustomerItemModel;
import com.webbdealer.detailing.customer.dto.CustomerItemModelAssembler;
import com.webbdealer.detailing.customer.dto.CustomerModel;
import com.webbdealer.detailing.customer.dto.CustomerModelAssembler;
import com.webbdealer.detailing.security.CustomUserDetails;
import com.webbdealer.detailing.security.JwtClaim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    private CustomerModelAssembler customerModelAssembler;

    private CustomerItemModelAssembler customerItemModelAssembler;

    private PagedResourcesAssembler<Customer> pagedResourcesAssembler;

    @Autowired
    public CustomerController(
            CustomerService customerService,
            CustomerModelAssembler customerModelAssembler,
            CustomerItemModelAssembler customerItemModelAssembler,
            PagedResourcesAssembler<Customer> pagedResourcesAssembler) {
        this.customerService = customerService;
        this.customerModelAssembler = customerModelAssembler;
        this.customerItemModelAssembler = customerItemModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(path = "", produces = { "application/hal+json" })
    public PagedModel<CustomerItemModel> fetchCustomers(
            @RequestParam String type,
            Pageable pageable) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();
        Long companyId = userDetails.getCompanyId();

        logger.info(userDetails.toString());

        String customerType = type.toLowerCase();
        Page<Customer> customerPagedList = null;
        switch (type) {
            case "retail":
                customerPagedList = customerService.fetchCustomerByType(companyId, CustomerType.RETAIL, pageable);
                break;
            case "dealer":
                customerPagedList = customerService.fetchCustomerByType(companyId, CustomerType.DEALER, pageable);
                break;
            default:
                throw new RuntimeException("Invalid Customer Type!");
        }
        // This works but uses the Customer entity instead of the CustomerModel class
        return pagedResourcesAssembler.toModel(customerPagedList, customerItemModelAssembler);
    }

    @GetMapping(path = "/{id}", produces = { "application/hal+json" })
    public CustomerModel customerDetails(@PathVariable Long id) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        JwtClaim userDetails = (JwtClaim) auth.getPrincipal();
        Long companyId = userDetails.getCompanyId();

        Optional<Customer> optionalCustomer = customerService.fetchById(companyId, id);
        Customer customer = optionalCustomer.orElseThrow(EntityNotFoundException::new);

        return customerModelAssembler.toModel(customer);
    }

}
