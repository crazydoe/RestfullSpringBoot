package org.michal.org.michal.webservice.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by michal on 07.12.2017.
 */

@RestController
public class InfoController {

    @RequestMapping(
            value = "/api",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> getCustomerPhoneNumbers(){
        String info = "Available endpoints: \n\n" +
                "\t api/customers \t:(get, post)\n" +
                "\t api/customers/{customerId} \t:(get, put, delete)\n\n" +
                "\t api/customers/{customerId}/emails \t:(get, post) \n" +
                "\t api/customers/{customerId}/emails/{emailId} \t:(put, delete)\n\n" +
                "\t api/customers/{customerId}/addresses \t:(get, post)\n" +
                "\t api/customers/{customerId}/addresses/{addressId} \t:(put, delete)\n\n" +
                "\t api/customers/{customerId}/phoneNumbers \t:(get, post)\n" +
                "\t api/customers/{customerId}/phoneNumbers/{numberId} \t:(put, delete)\n";
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
}
