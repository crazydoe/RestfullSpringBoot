package org.michal;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.model.CustomerResponse;
import org.michal.org.michal.webservice.web.api.CustomerController;
import org.testng.annotations.BeforeTest;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */
public class FunctionalTest {

    protected CustomerResponse testCustomer;

    @BeforeTest
    public static void setup(){
        String port = System.getProperty("server.port");

        if (port == null) RestAssured.port = 8080;
        else RestAssured.port = Integer.valueOf(port);

        String basePath = System.getProperty("server.base");

        if(basePath==null) basePath = "/";

        RestAssured.basePath = basePath;
        String baseHost = System.getProperty("server.host");

        if(baseHost==null) baseHost = "http://localhost/api";

        RestAssured.baseURI = baseHost;
    }

    @Before
    public void DatabaseSetup() {
        Customer customer = new Customer()
                .setGender("test")
                .setName("Customer")
                .setSurname("Test");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).post("/api/customers");
        testCustomer = response.as(CustomerResponse.class);
    }

    @After
    public void CleanDatabase(){
        given().when().delete("/api/customers/"+testCustomer.getId());
    }
}
