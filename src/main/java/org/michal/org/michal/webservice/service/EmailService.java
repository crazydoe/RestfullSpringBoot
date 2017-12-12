package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.EmailAddress;

import java.util.Collection;

/**
 * Created by michal on 05.12.2017.
 */

public interface EmailService {

    Collection<EmailAddress> findByCustomerId(Long customerId);

    EmailAddress findOne(Long id);

    EmailAddress createEmailForCustomerId(Long customerId, EmailAddress email);

    EmailAddress updateEmailByCustomerId(Long customerId, EmailAddress email);

    boolean deleteEmailByCustomerId(Long customerId, Long emailId);

    void deleteCustomerAll(Long customerId);
}
