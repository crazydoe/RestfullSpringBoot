package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.Customer;

import java.util.Collection;

/**
 * Created by michal on 05.12.2017.
 */
public interface CustomerService {

    Iterable<Customer> findAll();

    Customer findOne(Long id);

    Customer create(Customer customer);

    Customer update(Customer customer);

    void delete(Long id);

}
