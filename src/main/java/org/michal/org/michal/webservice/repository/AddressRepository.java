package org.michal.org.michal.webservice.repository;

import org.michal.org.michal.webservice.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by michal on 06.12.2017.
 */
@Repository
public interface AddressRepository  extends CrudRepository<Address,  Long> {
}
