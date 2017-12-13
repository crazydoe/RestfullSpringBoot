package org.michal.org.michal.webservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by michal on 06.12.2017.
 */

@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    private Long customerId;
    private String phoneNumber;

    public PhoneNumber() {
    }

    public Long getId() {
        return id;
    }

    public PhoneNumber setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public PhoneNumber setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneNumber setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
