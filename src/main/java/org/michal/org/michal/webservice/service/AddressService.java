package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.Address;
import java.util.Collection;

/**
 * Created by michal on 06.12.2017.
 */

public interface AddressService {

    Collection<Address> findByCustomerId(Long customerId);

    Address findOne(Long id);

    Address createAddressForCustomerId(Long customerId, Address address);

    Address updateAddressByCustomerId(Long customerId, Address address);

    boolean deleteAddressByCustomerId(Long customerId, Long addressId);

    void deleteCustomerAll(Long customerId);
}
