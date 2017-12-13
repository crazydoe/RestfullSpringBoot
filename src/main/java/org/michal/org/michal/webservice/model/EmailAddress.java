package org.michal.org.michal.webservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by michal on 05.12.2017.
 */

@Entity
public class EmailAddress {
    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    private Long customerId;
    private String emailAddress;

    public EmailAddress() {
    }

    public Long getId() {
        return id;
    }

    public EmailAddress setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public EmailAddress setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public EmailAddress setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }
}
