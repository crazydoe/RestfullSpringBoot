package org.michal.org.michal.webservice.model;

import java.util.Collection;

/**
 * Created by michal on 06.12.2017.
 */

public class CustomerResponse {
    private Long id;
    private String name;
    private String surname;
    private String gender;
    private Collection<EmailAddress> emails;
    private Collection<PhoneNumber> phoneNumbers;
    private Collection<Address> addresses;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.gender = customer.getGender();
        this.surname = customer.getSurname();
        this.name = customer.getName();
    }

    public CustomerResponse() {
    }

    public Long getId() {
        return id;
    }

    public CustomerResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomerResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public CustomerResponse setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public CustomerResponse setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Collection<EmailAddress> getEmails() {
        return emails;
    }

    public CustomerResponse setEmails(Collection<EmailAddress> emails) {
        this.emails = emails;
        return this;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public CustomerResponse setPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    public CustomerResponse setAddresses(Collection<Address> addresses) {
        this.addresses = addresses;
        return this;
    }
}
