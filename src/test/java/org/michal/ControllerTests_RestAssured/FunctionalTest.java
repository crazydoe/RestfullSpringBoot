package org.michal.ControllerTests_RestAssured;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.michal.org.michal.webservice.model.*;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */
public class FunctionalTest {

    CustomerResponse testCustomer;
    Address testAddress;
    EmailAddress testEmail;
    PhoneNumber testPhoneNumber;

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
        Address address = new Address()
                .setAddress("test alley 404");
        EmailAddress emailAddress = new EmailAddress()
                .setEmailAddress("test@test.com");
        PhoneNumber phoneNumber = new PhoneNumber()
                .setPhoneNumber("1234567890");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(customer).post("/api/customers");
        testCustomer = response.as(CustomerResponse.class);

        response = given()
                .when().contentType(ContentType.JSON)
                .body(address).post("/api/customers/"+ testCustomer.getId() + "/addresses");
        testAddress = response.as(Address.class);

        response = given()
                .when().contentType(ContentType.JSON)
                .body(emailAddress).post("/api/customers/"+ testCustomer.getId() + "/emails");
        testEmail = response.as(EmailAddress.class);

        response = given()
                .when().contentType(ContentType.JSON)
                .body(phoneNumber).post("/api/customers/"+ testCustomer.getId() + "/phoneNumbers");
        testPhoneNumber = response.as(PhoneNumber.class);
    }

    @After
    public void CleanDatabase(){
        given().when()
                .delete("/api/customers/"+ testCustomer.getId() + "/addresses/" + testAddress.getId());
        given().when()
                .delete("/api/customers/"+ testCustomer.getId() + "/emails/" + testEmail.getId());
        given().when()
                .delete("/api/customers/"+ testCustomer.getId() + "/phoneNumbers/" + testPhoneNumber.getId());

        given().when().delete("/api/customers/"+testCustomer.getId());
    }

    static class Utils{
        static <T>List<T> responseToList(Class<?> type, String json) throws IOException{
            ObjectMapper mapper = new ObjectMapper();

            TypeFactory factory = mapper.getTypeFactory();
            CollectionType collectionType = factory.constructCollectionType(List.class, type);
            return mapper.readValue(json, collectionType);
        }
    }
}
