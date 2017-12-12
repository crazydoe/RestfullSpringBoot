package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.EmailAddress;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.michal.org.michal.webservice.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by michal on 05.12.2017.
 */

@Service
public class EmailServiceBean implements EmailService{

    private EmailRepository emailRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public EmailServiceBean(EmailRepository emailRepository, CustomerRepository customerRepository){
        this.emailRepository = emailRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public Collection<EmailAddress> findByCustomerId(Long customerId) {
        Iterable<EmailAddress> emails = emailRepository.findAll();

        Collection<EmailAddress> customerEmails = new ArrayList<>();

        emails.forEach(emailAddress -> {
            if(emailAddress.getCustomerId().equals(customerId))
                customerEmails.add(emailAddress);
        });

        return customerEmails;
    }

    @Override
    public EmailAddress findOne(Long id) {
        return emailRepository.findOne(id);
    }

    @Override
    public EmailAddress createEmailForCustomerId(Long customerId, EmailAddress email) {
        if(!validate(email) || customerRepository.findOne(customerId) == null){
            return null;
        }
        email.setCustomerId(customerId);
        return emailRepository.save(email);
    }

    @Override
    public EmailAddress updateEmailByCustomerId(Long customerId, EmailAddress email) {
        if(!validate(email) || customerRepository.findOne(customerId) == null
                || emailRepository.findOne(email.getId()) == null){
            return null;
        }
        email.setCustomerId(customerId);
        return emailRepository.save(email);

    }

    @Override
    public boolean deleteEmailByCustomerId(Long customerId, Long emailId) {
        if(customerRepository.findOne(customerId) == null || !emailRepository.exists(emailId))
            return false;

        emailRepository.delete(emailId);
        return true;
    }

    @Override
    public void deleteCustomerAll(Long customerId) {
        Collection<EmailAddress> customerEmails = findByCustomerId(customerId);
        emailRepository.delete(customerEmails);
    }

    private boolean validate(EmailAddress email){
        return(email != null && email.getEmailAddress() != null);
    }
}
