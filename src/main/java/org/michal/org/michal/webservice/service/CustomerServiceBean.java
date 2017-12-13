package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findOne(Long id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if(!validate(customer) || customer.getId() != null)
            return null;

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if(!validate(customer))
            return null;

        Customer customerPersisted = findOne(customer.getId());
        if(customerPersisted == null)
            return null;

        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if(!customerRepository.exists(id)) return;
        emailService.deleteCustomerAll(id);
        addressService.deleteCustomerAll(id);
        numberService.deleteCustomerAll(id);
        customerRepository.delete(id);
    }

    private boolean validate(Customer customer){
        return(customer != null
                && customer.getSurname() != null
                && customer.getName() != null
                && customer.getGender() != null);
    }
}
