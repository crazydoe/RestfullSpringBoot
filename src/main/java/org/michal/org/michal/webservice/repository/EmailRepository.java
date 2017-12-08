package org.michal.org.michal.webservice.repository;

import org.michal.org.michal.webservice.model.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by michal on 05.12.2017.
 */
@Repository
public interface EmailRepository extends CrudRepository<EmailAddress,  Long> {


}
