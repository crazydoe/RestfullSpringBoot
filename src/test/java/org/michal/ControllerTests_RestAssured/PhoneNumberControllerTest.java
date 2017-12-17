package org.michal.ControllerTests_RestAssured;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.michal.org.michal.webservice.model.PhoneNumber;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */
public class PhoneNumberControllerTest extends FunctionalTest{

    @Test
    public void PostPhoneNumber_CustomerIdInPathAndPhoneNumberInBody_201CreatedAndNewPhoneNumberRetrieved(){
        PhoneNumber number = new PhoneNumber()
                .setPhoneNumber("123123123");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(number).post("/api/customers/" +testCustomer.getId() + "/phoneNumbers");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(response.as(PhoneNumber.class));
    }

    @Test
    public void PostPhoneNumber_WrongCustomerIdInPath_400BadRequest(){
        PhoneNumber number = new PhoneNumber()
                .setPhoneNumber("123123123");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(number).post("/api/customers/0/phoneNumbers");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PostPhoneNumber_CustomerIdInPathEmptyBody_400BadRequest(){
        Assert.assertEquals(
                given().when().contentType(ContentType.JSON)
                        .post("/api/customers/" +testCustomer.getId() + "/phoneNumbers")
                        .getStatusCode(),
                400
        );
    }

    @Test
    public void PutPhoneNumber_CustomerIdAndPhoneNumberIdInPath_200OKAndUpdatedPhoneNumberRetrieved(){
        PhoneNumber updated = new PhoneNumber()
                .setPhoneNumber("123123123");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(updated)
                .put("/api/customers/" +testCustomer.getId() + "/phoneNumbers/" + testPhoneNumber.getId());
        PhoneNumber retrieved = response.as(PhoneNumber.class);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(testPhoneNumber.getPhoneNumber(), retrieved.getPhoneNumber());
        Assert.assertEquals(retrieved.getPhoneNumber(), updated.getPhoneNumber());
        Assert.assertEquals(retrieved.getCustomerId(), testPhoneNumber.getCustomerId());
        Assert.assertEquals(retrieved.getId(), testPhoneNumber.getId());
    }

    @Test
    public void PutPhoneNumber_WrongCustomerId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new PhoneNumber().setPhoneNumber("123123123"))
                .put("/api/customers/0/phoneNumbers/" + testPhoneNumber.getId());

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PutPhoneNumber_WrongPhoneNumberId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new PhoneNumber().setPhoneNumber("123123123"))
                .put("/api/customers/"+testCustomer.getId()+"/phoneNumbers/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PutPhoneNumber_EmptyBody_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new PhoneNumber())
                .put("/api/customers/"+testCustomer.getId()+"/phoneNumbers/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void GetPhoneNumbers_CustomerIdInPath_200OkAndCollectionOfPhoneNumber() throws IOException {
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/"+testCustomer.getId()+"/phoneNumbers");


        List<PhoneNumber> retrievedNumbers = Utils.responseToList(
                PhoneNumber.class,
                response.getBody().asString()
        );

        assert retrievedNumbers != null;
        retrievedNumbers.forEach(emailResponse -> {
            Assert.assertNotNull(emailResponse.getId());
        });
    }

    @Test
    public void GetPhoneNumbers_WrongCustomerId_404NotFound(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/0/phoneNumbers");

        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void DeletePhoneNumber_CustomerIdAndPhoneNumberIdInPath_204NoContent(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/"+testCustomer.getId()+"/phoneNumbers/" + testPhoneNumber.getId());

        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    public void DeletePhoneNumber_WrongCustomerId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/0/phoneNumbers/" + testPhoneNumber.getId());

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void DeletePhoneNumber_WrongPhoneNumberId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/"+testCustomer.getId()+"/phoneNumbers/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
