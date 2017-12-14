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

    private Long customerId;


    @Test
    public void PushCustomer_CorrectCustomerDataAsBody_201Created() {
        Customer customer = new Customer()
                .setGender("test")
                .setName("Customer")
                .setSurname("Test");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).post("/api/customers");
        Assert.assertEquals(response.getStatusCode(), 201);
        customerId = response.as(Customer.class).getId();
    }

    @Test
    public void PushCustomer_EmptyDataAsBody_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new Customer()).post("/api/customers");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void GetAllCustomers_URLRequest_200OK(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void GetCustomer_CustomerIdInURLPath_200OK(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/"+customerId);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void GetCustomer_NonExistentCustomerIdInURLPath_404NotFound(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/0");
        Assert.assertEquals(response.getStatusCode(), 404);
    }


    @Test
    public void PutCustomer_CorrectCustomerIdInPath_200OkAndUpdatedResponseInBody(){
        Customer customer = new Customer()
                .setGender("test")
                .setName("Customer")
                .setSurname("TestPut");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).put("/api/customers/"+customerId);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.as(Customer.class).getSurname(), customer.getSurname());
    }

    @Test
    public void PutCustomer_CorrectCustomerIdEmptyBody_500InternalServerError(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new Customer()).put("/api/customers/0");
        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test
    public void PutCustomer_IncorrectCustomerIdInPath_500InternalServerError(){
        Customer customer = new Customer()
                .setGender("test")
                .setName("Customer")
                .setSurname("TestPut");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).put("/api/customers/0");
        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test
    public void DeleteCustomer_IncorrectCustomerIdInPath_404NotFound(){
        Response response = given()
                .when()
                .delete("/api/customers/0");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void DeleteCustomer_CorrectCustomerIdInPath_204NoContent(){
        Response response = given()
                .when()
                .delete("/api/customers/"+customerId);
        Assert.assertEquals(response.getStatusCode(), 204);
    }










}
