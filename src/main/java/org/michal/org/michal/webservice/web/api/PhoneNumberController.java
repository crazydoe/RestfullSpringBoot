package org.michal.org.michal.webservice.web.api;

import org.michal.org.michal.webservice.model.EmailAddress;
import org.michal.org.michal.webservice.model.PhoneNumber;
import org.michal.org.michal.webservice.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by michal on 06.12.2017.
 */
@RestController
public class PhoneNumberController {

    private PhoneNumberService numberService;

    @Autowired
    public PhoneNumberController(PhoneNumberService numberService){
        this.numberService = numberService;
    }

    @RequestMapping(
            value = "/api/customers/{customerId}/phoneNumbers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Iterable<PhoneNumber>> getCustomerPhoneNumbers(
            @PathVariable("customerId") Long customerId){
        Iterable<PhoneNumber> numbers = numberService.findByCustomerId(customerId);
        if(numbers == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(numbers, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/phoneNumbers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PhoneNumber> createPhoneNumber(
            @RequestBody PhoneNumber number, @PathVariable("customerId") Long customerId){
        PhoneNumber newNumber = numberService.createNumberForCustomerId(customerId, number);
        if(newNumber == null)
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(newNumber, HttpStatus.CREATED);
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/phoneNumbers/{numberId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PhoneNumber> updatePhoneNumber(
            @RequestBody PhoneNumber number, @PathVariable("customerId") Long customerId,
            @PathVariable("numberId") Long numberId){
                number.setId(numberId);
                PhoneNumber updatedNumber= numberService.updateNumberByCustomerId(customerId, number);
                if(updatedNumber == null)
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(updatedNumber, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/phoneNumbers/{numberId}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<EmailAddress> deletePhoneNumber(
            @PathVariable("customerId") Long customerId, @PathVariable("numberId") Long numberId){
        if(numberService.deleteNumberByCustomerId(customerId, numberId))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
