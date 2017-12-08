package org.michal.org.michal.webservice.service;
import org.michal.org.michal.webservice.model.PhoneNumber;
import java.util.Collection;

/**
 * Created by michal on 06.12.2017.
 */

public interface PhoneNumberService {

    Collection<PhoneNumber> findByCustomerId(Long customerId);

    PhoneNumber findOne(Long id);

    PhoneNumber create(Long customerId, PhoneNumber phoneNumber);

    PhoneNumber update(Long customerId, PhoneNumber phoneNumber);

    boolean delete(Long customerId,Long numberId);

    void deleteCustomerAll(Long customerId);
}
