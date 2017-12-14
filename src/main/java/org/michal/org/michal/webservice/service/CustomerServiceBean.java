package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.model.CustomerResponse;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by michal on 05.12.2017.
 */

@Service
public class CustomerServiceBean implements CustomerService {

    private CustomerRepository customerRepository;
    private EmailService emailService;
    private AddressService addressService;
    private PhoneNumberService numberService;


    @Autowired
    public CustomerServiceBean(CustomerRepository customerRepository, EmailService emailService,
                               AddressService addressService, PhoneNumberService numberService){
        this.numberService = numberService;
        this.addressService = addressService;
        this.emailService = emailService;
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<CustomerResponse> findAll() {
        Iterable<Customer> customers = customerRepository.findAll();
        if(customers == null ) return null;

        Collection<CustomerResponse> customerResponses = new ArrayList<>();
        customers.forEach(customer -> customerResponses.add(
                new CustomerResponse(customer)
                        .setEmails(emailService.findByCustomerId(customer.getId()))
                        .setAddresses(addressService.findByCustomerId(customer.getId()))
                        .setPhoneNumbers(numberService.findByCustomerId(customer.getId()))
                )
        );

        return customerResponses;
    }

    @Override
    public CustomerResponse findOne(Long id) {
        Customer customer = customerRepository.findOne(id);
        if(customer != null)
            return new CustomerResponse(customer)
                .setEmails(emailService.findByCustomerId(customer.getId()))
                .setAddresses(addressService.findByCustomerId(customer.getId()))
                .setPhoneNumbers(numberService.findByCustomerId(customer.getId()));
        return null;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if(!validate(customer) || customer.getId() != null)
            return null;

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if(!validate(customer) || !customerRepository.exists(customer.getId()))
            return null;

        return customerRepository.save(customer);
    }

    @Override
    public boolean deleteCustomer(Long id) {
        if(!customerRepository.exists(id))
            return false;
        emailService.deleteCustomerAll(id);
        addressService.deleteCustomerAll(id);
        numberService.deleteCustomerAll(id);
        customerRepository.delete(id);
        return true;
    }

    private boolean validate(Customer customer){
        return(customer != null
                && customer.getSurname() != null
                && customer.getName() != null
                && customer.getGender() != null);
    }
}
