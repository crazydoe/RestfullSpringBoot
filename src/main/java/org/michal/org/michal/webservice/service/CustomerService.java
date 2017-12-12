package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.Customer;


/**
 * Created by michal on 05.12.2017.
 */
public interface CustomerService {

    Iterable<Customer> findAll();

    Customer findOne(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Long id);

}
