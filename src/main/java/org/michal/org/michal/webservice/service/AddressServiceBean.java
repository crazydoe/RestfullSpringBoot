package org.michal.org.michal.webservice.service;

import org.michal.org.michal.webservice.model.Address;
import org.michal.org.michal.webservice.repository.AddressRepository;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by michal on 06.12.2017.
 */

@Service
public class AddressServiceBean implements AddressService{

    private AddressRepository addressRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public AddressServiceBean(AddressRepository addressRepository, CustomerRepository customerRepository){
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public Collection<Address> findByCustomerId(final Long customerId) {
        Iterable<Address> addresses = addressRepository.findAll();

        final Collection<Address> customerAddresses = new ArrayList<>();

        addresses.forEach(address -> {
            if(address.getCustomerId().equals(customerId))
                customerAddresses.add(address);
        });

        return customerAddresses;
    }

    @Override
    public Address findOne(Long id){
        return addressRepository.findOne(id);
    }

    @Override
    public Address createAddressForCustomerId(Long customerId, Address address) {
        if(!validate(address) || !customerRepository.exists(customerId))
            return null;
        address.setCustomerId(customerId);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddressByCustomerId(Long customerId, Address address) {
        if(!validate(address) || !customerRepository.exists(customerId)
                || !addressRepository.exists(address.getId())){
            return null;
        }
        address.setCustomerId(customerId);
        return addressRepository.save(address);
    }

    @Override
    public boolean deleteAddressByCustomerId(Long customerId, Long addressId) {
        if(!customerRepository.exists(customerId) || !addressRepository.exists(addressId))
            return false;
        addressRepository.delete(addressId);
        return true;
    }

    @Override
    public void deleteCustomerAll(Long customerId) {
        Collection<Address> customerAddresses = findByCustomerId(customerId);
        addressRepository.delete(customerAddresses);
    }

    private boolean validate(Address address){
        return(address != null && address.getAddress() != null);
    }

}
