package org.michal.RestAssuredTests;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.michal.org.michal.webservice.model.Address;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */
public class AddressResponseTest extends FunctionalTest{

    @Test
    public void PostAddress_CustomerIdInPathAndAddressInBody_201CreatedAndNewAddressRetrieved(){
        Address address = new Address()
                .setAddress("test alley 404");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(address).post("/api/customers/" +testCustomer.getId() + "/addresses");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(response.as(Address.class));
    }

    @Test
    public void PostAddress_WrongCustomerIdInPathAndAddressInBody_400BadRequest(){
        Address address = new Address()
                .setAddress("test alley 404");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(address).post("/api/customers/0/addresses");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PostAddress_CustomerIdInPathEmptyBody_400BadRequest(){
        Assert.assertEquals(
                given().when().contentType(ContentType.JSON)
                        .post("/api/customers/" +testCustomer.getId() + "/addresses")
                        .getStatusCode(),
                400
        );
    }

    @Test
    public void PutAddress_CustomerIdAndAddressIdInPath_200OKAndUpdatedAddressRetrieved(){
        Address updated = new Address()
                .setAddress("test alley 200");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(updated)
                .put("/api/customers/" +testCustomer.getId() + "/addresses/" + testAddress.getId());
        Address retrieved = response.as(Address.class);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(testAddress.getAddress(), retrieved.getAddress());
        Assert.assertEquals(retrieved.getAddress(), updated.getAddress());
        Assert.assertEquals(retrieved.getCustomerId(), testAddress.getCustomerId());
        Assert.assertEquals(retrieved.getId(), testAddress.getId());
    }

    @Test
    public void PutAddress_WrongCustomerId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new Address().setAddress("test alley 200"))
                .put("/api/customers/0/addresses/" + testAddress.getId());

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PutAddress_WrongAddressId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new Address().setAddress("test alley 200"))
                .put("/api/customers/"+testCustomer.getId()+"/addresses/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PutAddress_EmptyBody_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new Address())
                .put("/api/customers/"+testCustomer.getId()+"/addresses/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void GetAddress_CustomerIdInPath_200OkAndCollectionOfAddresses() throws IOException {
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/"+testCustomer.getId()+"/addresses");


        List<Address> retrievedAddresses = Utils.responseToList(
                Address.class,
                response.getBody().asString()
        );

        assert retrievedAddresses != null;
        retrievedAddresses.forEach(addressResponse -> {
            Assert.assertNotNull(addressResponse.getId());
        });
    }

    @Test
    public void GetAddresses_WrongCustomerId_404NotFound(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/0/addresses");

        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void DeleteAddress_CustomerIdAndAddressIdInPath_204NoContent(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/"+testCustomer.getId()+"/addresses/" + testAddress.getId());

        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    public void DeleteAddress_WrongCustomerId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/0/addresses/" + testAddress.getId());

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void DeleteAddress_WrongAddressId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/"+testCustomer.getId()+"/addresses/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
