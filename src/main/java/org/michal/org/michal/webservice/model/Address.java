package org.michal.org.michal.webservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by michal on 06.12.2017.
 */

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    private Long customerId;
    private String address;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public Address setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Address setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Address setAddress(String address) {
        this.address = address;
        return this;
    }


}
