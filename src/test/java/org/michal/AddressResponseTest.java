package org.michal;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.michal.org.michal.webservice.model.Address;
import org.testng.Assert;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */
public class AddressResponseTest extends FunctionalTest{

    @Test
    public void CreateAddress_CustomerIdAsPathValueAndAddressInBody_201Created(){
        Address address = new Address()
                .setAddress("somewhere");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(address).post("/api/customers/1/addresses");
        Assert.assertEquals(response.getStatusCode(), 201);
    }
}
