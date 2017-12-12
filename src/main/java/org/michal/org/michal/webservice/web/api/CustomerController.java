package org.michal.org.michal.webservice.web.api;

import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.model.CustomerResponse;
import org.michal.org.michal.webservice.service.AddressService;
import org.michal.org.michal.webservice.service.CustomerService;
import org.michal.org.michal.webservice.service.EmailService;
import org.michal.org.michal.webservice.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by michal on 05.12.2017.
 */

@RestController
public class CustomerController {

    private CustomerService customerService;
    private EmailService emailService;
    private AddressService addressService;
    private PhoneNumberService numberService;

    @Autowired
    public CustomerController(CustomerService customerService, EmailService emailService,
                              AddressService addressService, PhoneNumberService numberService){
        this.customerService = customerService;
        this.emailService = emailService;
        this.addressService = addressService;
        this.numberService = numberService;
    }


    @RequestMapping(
            value = "/api/customers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<CustomerResponse>> getCustomers(){
        Iterable<Customer> customers = customerService.findAll();
        Collection<CustomerResponse> customerResponses = new ArrayList<>();

        customers.forEach(customer -> customerResponses.add(
                new CustomerResponse(
                        customer,
                        emailService.findByCustomerId(customer.getId()),
                        numberService.findByCustomerId(customer.getId()),
                        addressService.findByCustomerId(customer.getId()))
                )
        );

        return new ResponseEntity<>(customerResponses, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Long id){
        Customer customer = customerService.findOne(id);

        if(customer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        CustomerResponse customerResponse = new CustomerResponse(
                customer,
                emailService.findByCustomerId(customer.getId()),
                numberService.findByCustomerId(customer.getId()),
                addressService.findByCustomerId(customer.getId())
                );

        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
         Customer newCustomer = customerService.createCustomer(customer);
         if(newCustomer == null)
             return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
         return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }


    @RequestMapping(
            value = "/api/customers/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id){
        customer.setId(id);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        if(updatedCustomer == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){

        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}