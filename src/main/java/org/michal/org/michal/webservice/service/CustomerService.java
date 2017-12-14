package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.model.CustomerResponse;

import java.util.Collection;


/**
 * Created by michal on 05.12.2017.
 */
public interface CustomerService {

    Collection<CustomerResponse> findAll();

    CustomerResponse findOne(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    boolean deleteCustomer(Long id);

}
