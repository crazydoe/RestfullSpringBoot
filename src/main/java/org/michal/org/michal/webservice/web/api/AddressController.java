package org.michal.org.michal.webservice.web.api;

import org.michal.org.michal.webservice.model.Address;
import org.michal.org.michal.webservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by michal on 06.12.2017.
 */
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(
            value = "/api/customers/{customerId}/addresses",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<Address>> getCustomerAddresses(
            @PathVariable("customerId") Long customerId){
        Collection<Address> addresses= addressService.findByCustomerId(customerId);
        if(addresses == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(addresses, HttpStatus.OK);

    }

    @RequestMapping(
            value = "/api/customers/{customerId}/addresses",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Address> createAddress(
            @RequestBody Address address, @PathVariable("customerId") Long id){
        Address newAddress = addressService.create(id, address);
        if(newAddress == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/addresses/{addressId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Address> updateAddress(
            @RequestBody Address address, @PathVariable("customerId") Long customerId,
            @PathVariable("addressId") Long addressId){
        address.setId(addressId);
        Address updatedAddress = addressService.update(customerId, address);
        if(updatedAddress == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/addresses/{addressId}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Address> deleteAddress(
            @PathVariable("customerId") Long customerId, @PathVariable("addressId") Long addressId){
        if(addressService.delete(customerId, addressId))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
