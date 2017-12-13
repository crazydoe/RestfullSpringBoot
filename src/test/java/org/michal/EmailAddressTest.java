package org.michal;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.michal.org.michal.webservice.model.EmailAddress;
import org.testng.Assert;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */
public class EmailAddressTest extends FunctionalTest {

    @Test
    public void CreateEmailAddress_CustomerIdAsPathValueAndEmailInBody_201Created(){
        EmailAddress emailAddress = new EmailAddress()
                .setEmailAddress("adres@testowy.com");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(emailAddress).post("/api/customers/1/emails");
        Assert.assertEquals(response.getStatusCode(), 201);

    }
}
