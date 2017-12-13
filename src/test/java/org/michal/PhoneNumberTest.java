package org.michal;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.michal.org.michal.webservice.model.PhoneNumber;
import org.testng.Assert;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */
public class PhoneNumberTest extends FunctionalTest{

    @Test
    public void CreatePhoneNumber_CustomerIdAsPathValueAndNumberInBody_201Created(){
        PhoneNumber phoneNumber = new PhoneNumber()
                .setPhoneNumber("123456789");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(phoneNumber).post("/api/customers/1/phoneNumbers");
        Assert.assertEquals(response.getStatusCode(), 201);
    }

}
