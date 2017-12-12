package org.michal.org.michal.webservice.service;
import org.michal.org.michal.webservice.model.PhoneNumber;
import java.util.Collection;

/**
 * Created by michal on 06.12.2017.
 */

public interface PhoneNumberService {

    Collection<PhoneNumber> findByCustomerId(Long customerId);

    PhoneNumber findOne(Long id);

    PhoneNumber createNumberForCustomerId(Long customerId, PhoneNumber phoneNumber);

    PhoneNumber updateNumberByCustomerId(Long customerId, PhoneNumber phoneNumber);

    boolean deleteNumberByCustomerId(Long customerId,Long numberId);

    void deleteCustomerAll(Long customerId);
}
