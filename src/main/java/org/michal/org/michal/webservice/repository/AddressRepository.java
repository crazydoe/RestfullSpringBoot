package org.michal.org.michal.webservice.repository;

import org.michal.org.michal.webservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michal on 06.12.2017.
 */
public interface AddressRepository  extends CrudRepository<Address,  Long> {
}
