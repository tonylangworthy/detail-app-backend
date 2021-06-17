package com.webbdealer.detailing.customer.dto;

import com.webbdealer.detailing.customer.CustomerController;
import com.webbdealer.detailing.customer.dao.Customer;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerItemModelAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerItemModel> {


    public CustomerItemModelAssembler() {
        super(CustomerController.class, CustomerItemModel.class);
    }

    @Override
    public CustomerItemModel toModel(Customer customer) {
        CustomerItemModel customerItem = new CustomerItemModel();
        customerItem.setId(customer.getId());
        customerItem.setCustomerType(customer.getCustomerType());
        customerItem.setFullName(customer.getFirstName() + " " + customer.getLastName());
        customerItem.setBusiness(customer.getBusiness());
        customerItem.setPhone(customer.getPhone());

        Link selfLink = linkTo(methodOn(CustomerController.class).customerDetails(customerItem.getId())).withSelfRel();

        // Need to add create, update and delete links
        customerItem.add(selfLink);

        return customerItem;
    }
}
