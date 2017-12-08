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

    public Address() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
