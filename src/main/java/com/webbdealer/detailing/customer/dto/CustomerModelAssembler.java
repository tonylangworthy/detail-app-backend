package com.webbdealer.detailing.customer.dto;

import com.webbdealer.detailing.customer.CustomerController;
import com.webbdealer.detailing.customer.dao.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerModel> {

    public CustomerModelAssembler() {
        super(CustomerController.class, CustomerModel.class);
    }

    @Override
    public CustomerModel toModel(Customer entity) {
        CustomerModel resource = new CustomerModel();
        resource.setId(entity.getId());
        resource.setCustomerType(entity.getCustomerType());
        resource.setFirstName(entity.getFirstName());
        resource.setLastName(entity.getLastName());
        resource.setBusiness(entity.getBusiness());
        resource.setEmail(entity.getEmail());
        resource.setPhone(entity.getPhone());
        resource.setReceiveTexts(entity.isReceiveTexts());
        resource.setNotes(entity.getNotes());
        resource.setCreatedAt(entity.getCreatedAt());
        resource.setUpdatedAt(entity.getUpdatedAt());

        Link customers = linkTo(methodOn(CustomerController.class).fetchCustomers("retail", null))
                .withRel(LinkRelation.of("customers"));

        resource.add(customers);

        return resource;
    }

    @Override
    public CollectionModel<CustomerModel> toCollectionModel(Iterable<? extends Customer> customers) {
        return super.toCollectionModel(customers);
    }

}
