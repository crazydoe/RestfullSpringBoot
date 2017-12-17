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


    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @RequestMapping(
            value = "/api/customers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<CustomerResponse>> getCustomers(){
        Collection<CustomerResponse> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Long id){
        CustomerResponse customer = customerService.findOne(id);
        if(customer == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
         Customer newCustomer = customerService.createCustomer(customer);
         if(newCustomer == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        if(updatedCustomer == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){
        if(customerService.deleteCustomer(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}