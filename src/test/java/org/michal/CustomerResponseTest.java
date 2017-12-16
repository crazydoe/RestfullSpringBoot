package org.michal;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.model.CustomerResponse;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */

public class CustomerResponseTest extends FunctionalTest{

    @Test
    public void PushCustomer_CorrectCustomerDataAsBody_201CreatedAndNewCustomerRetrieved() {
        Customer customer = new Customer()
                .setGender("test")
                .setName("Customer")
                .setSurname("Test");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).post("/api/customers");

        Assert.assertEquals(response.getStatusCode(), 201);

        CustomerResponse receivedCustomer = response.as(CustomerResponse.class);

        Assert.assertNotNull(receivedCustomer.getId());
        Assert.assertEquals(customer.getName(), receivedCustomer.getName());
        Assert.assertEquals(customer.getSurname(), receivedCustomer.getSurname());
        Assert.assertEquals(customer.getGender(), receivedCustomer.getGender());
    }

    @Test
    public void PushCustomer_EmptyDataAsBody_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new Customer()).post("/api/customers");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void GetAllCustomers_URLRequest_200OKAndAllCustomersRetrieved() throws IOException {
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers");
        Assert.assertEquals(response.getStatusCode(), 200);

        List<CustomerResponse> receivedCustomers = Utils.responseToList(                                                // I guess it can be done in more brilliant way, but i just simply don't know how... sorry
                CustomerResponse.class,
                response.getBody().asString()
        );

        assert receivedCustomers != null;
        receivedCustomers.forEach(customerResponse -> {
            Assert.assertNotNull(customerResponse.getId());
        });
    }

    @Test
    public void GetCustomer_CustomerIdInURLPath_200OKAndCustomerRetrieved(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/"+testCustomer.getId());
        CustomerResponse retrievedCustomer = response.as(CustomerResponse.class);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(retrievedCustomer.getId());
        Assert.assertEquals(testCustomer.getName(), retrievedCustomer.getName());
        Assert.assertEquals(testCustomer.getGender(), retrievedCustomer.getGender());
        Assert.assertEquals(testCustomer.getSurname(), retrievedCustomer.getSurname());
    }

    @Test
    public void GetCustomer_NonExistentCustomerIdInURLPath_404NotFound(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/0");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void PutCustomer_CorrectCustomerIdInPath_200OkAndUpdatedCustomerRetrieved(){
        Customer customer = new Customer()
                .setGender("test")
                .setName("Customer")
                .setSurname("TestPut");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).put("/api/customers/"+testCustomer.getId());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.as(Customer.class).getSurname(), customer.getSurname());
    }

    @Test
    public void PutCustomer_CorrectCustomerIdEmptyBody_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new Customer()).put("/api/customers/0");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PutCustomer_IncorrectCustomerIdInPath_400BadRequest(){
        Customer customer = new Customer()
                .setGender("test")
                .setName("Customer")
                .setSurname("TestPut");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).put("/api/customers/0");
        Assert.assertEquals(response.getStatusCode(), 400);
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
                .delete("/api/customers/"+testCustomer.getId());
        Assert.assertEquals(response.getStatusCode(), 204);
    }


}
