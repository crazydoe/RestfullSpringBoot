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

    public CustomerResponse(Customer customer, Collection<EmailAddress> emails,
                            Collection<PhoneNumber> phoneNumbers, Collection<Address> addresses) {
        this.id = customer.getId();
        this.gender = customer.getGender();
        this.surname = customer.getSurname();
        this.name = customer.getName();
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.addresses = addresses;
    }

    public Collection<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Collection<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<Address> addresses) {
        this.addresses = addresses;
    }

    public Collection<EmailAddress> getEmails() {
        return emails;
    }

    public void setEmails(Collection<EmailAddress> emails) {
        this.emails = emails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
