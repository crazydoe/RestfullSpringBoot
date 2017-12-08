package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.PhoneNumber;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.michal.org.michal.webservice.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by michal on 06.12.2017.
 */

@Service
public class PhoneNumberServiceBean implements PhoneNumberService {

    private PhoneNumberRepository phoneNumberRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public PhoneNumberServiceBean(PhoneNumberRepository numberRepository, CustomerRepository customerRepository){
        this.phoneNumberRepository = numberRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<PhoneNumber> findByCustomerId(Long customerId) {
        Iterable<PhoneNumber> numbers = phoneNumberRepository.findAll();

        Collection<PhoneNumber> customerNumbers = new ArrayList<>();

        numbers.forEach(phoneNumber -> {
            if(phoneNumber.getCustomerId().equals(customerId))
                customerNumbers.add(phoneNumber);
        });

        return customerNumbers;
    }

    @Override
    public PhoneNumber findOne(Long id) {
        return phoneNumberRepository.findOne(id);
    }

    @Override
    public PhoneNumber create(Long customerId, PhoneNumber phoneNumber) {
        if(!validate(phoneNumber) || customerRepository.findOne(customerId) == null)
            return null;

        phoneNumber.setCustomerId(customerId);
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public PhoneNumber update(Long customerId, PhoneNumber phoneNumber){
        if(!validate(phoneNumber) || customerRepository.findOne(customerId) == null
                || phoneNumberRepository.findOne(phoneNumber.getId()) == null){
            return null;
        }
        phoneNumber.setCustomerId(customerId);
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public boolean delete(Long customerId, Long numberId) {
        if(customerRepository.findOne(customerId) == null || !phoneNumberRepository.exists(numberId))
            return false;

        phoneNumberRepository.delete(numberId);
        return true;
    }

    @Override
    public void deleteCustomerAll(Long customerId) {
        Collection<PhoneNumber> customerNumbers = findByCustomerId(customerId);
        phoneNumberRepository.delete(customerNumbers);
    }

    private boolean validate(PhoneNumber phoneNumber){
        return(phoneNumber != null && phoneNumber.getPhoneNumber() != null);
    }
}
