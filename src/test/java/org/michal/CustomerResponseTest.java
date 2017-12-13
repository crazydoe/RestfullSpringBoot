package org.michal;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.michal.org.michal.webservice.model.Customer;
import org.testng.Assert;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */

public class CustomerResponseTest extends FunctionalTest{

    @Test
    public void CreateCustomer_CorrectCustomerDataAsBody_201Created() {
        Customer customer = new Customer()
                .setGender("male")
                .setName("Maniek")
                .setSurname("Testowy");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).post("/api/customers");
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void CreateCustomer_EmptyDataAsBody_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new Customer()).post("/api/customers");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void GetCustomers_URLRequest_200OK(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void GetCustomer_CustomerIdInURLPath_200OK(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/1");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void GetCustomer_NonExistentCustomerIdInURLPath_404NotFound(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/0");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    

}
