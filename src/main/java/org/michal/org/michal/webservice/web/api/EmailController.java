package org.michal.org.michal.webservice.web.api;

import org.michal.org.michal.webservice.model.EmailAddress;
import org.michal.org.michal.webservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.Iterable;

/**
 * Created by michal on 05.12.2017.
 */

@RestController
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/emails",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Iterable<EmailAddress>> getCustomerEmails(
            @PathVariable("customerId") Long custId){
        Iterable<EmailAddress> emails = emailService.findByCustomerId(custId);
        if(emails == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/emails",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EmailAddress> createEmail(
            @RequestBody EmailAddress email, @PathVariable("customerId") Long customerId){
        EmailAddress newEmail = emailService.createEmailForCustomerId(customerId, email);
        if(newEmail == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(newEmail, HttpStatus.CREATED);
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/emails/{emailId}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EmailAddress> updateEmail(
            @RequestBody EmailAddress email, @PathVariable("customerId") Long customerId,
            @PathVariable("emailId") Long emailId){
                email.setId(emailId);
                EmailAddress updatedEmail = emailService.updateEmailByCustomerId(customerId, email);
                if(updatedEmail == null)
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(updatedEmail, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/api/customers/{customerId}/emails/{emailId}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<EmailAddress> deleteEmail(
            @PathVariable("customerId") Long customerId, @PathVariable("emailId") Long emailId){
        if(emailService.deleteEmailByCustomerId(customerId, emailId))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
