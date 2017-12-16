package org.michal;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.hibernate.validator.constraints.Email;
import org.junit.Test;
import org.michal.org.michal.webservice.model.EmailAddress;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by michal on 13.12.2017.
 */
public class EmailAddressResponseTest extends FunctionalTest {

    @Test
    public void PostEmail_CustomerIdInPathAndEmailInBody_201CreatedAndNewEmailRetrieved(){
        EmailAddress address = new EmailAddress()
                .setEmailAddress("test@test.com");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(address).post("/api/customers/" +testCustomer.getId() + "/emails");
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(response.as(EmailAddress.class));
    }

    @Test
    public void PostEmail_WrongCustomerIdInPath_400BadRequest(){
        EmailAddress address = new EmailAddress()
                .setEmailAddress("test@test.com");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(address).post("/api/customers/0/emails");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PostEmail_CustomerIdInPathEmptyBody_400BadRequest(){
        Assert.assertEquals(
                given().when().contentType(ContentType.JSON)
                        .post("/api/customers/" +testCustomer.getId() + "/emails")
                        .getStatusCode(),
                400
        );
    }

    @Test
    public void PutEmail_CustomerIdAndEmailIdInPath_200OKAndUpdatedEmailRetrieved(){
        EmailAddress updated = new EmailAddress()
                .setEmailAddress("another@test.com");

        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(updated)
                .put("/api/customers/" +testCustomer.getId() + "/emails/" + testEmail.getId());
        EmailAddress retrieved = response.as(EmailAddress.class);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotEquals(testEmail.getEmailAddress(), retrieved.getEmailAddress());
        Assert.assertEquals(retrieved.getEmailAddress(), updated.getEmailAddress());
        Assert.assertEquals(retrieved.getCustomerId(), testEmail.getCustomerId());
        Assert.assertEquals(retrieved.getId(), testEmail.getId());
    }

    @Test
    public void PutEmail_WrongCustomerId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new EmailAddress().setEmailAddress("another@test.com"))
                .put("/api/customers/0/emails/" + testEmail.getId());

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PutEmail_WrongEmailId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new EmailAddress().setEmailAddress("another@test.com"))
                .put("/api/customers/"+testCustomer.getId()+"/emails/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void PutEmail_EmptyBody_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(new EmailAddress())
                .put("/api/customers/"+testCustomer.getId()+"/emails/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void GetEmails_CustomerIdInPath_200OkAndCollectionOfEmails() throws IOException {
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/"+testCustomer.getId()+"/emails");


        List<EmailAddress> retrievedEmails = Utils.responseToList(
                EmailAddress.class,
                response.getBody().asString()
        );

        assert retrievedEmails != null;
        retrievedEmails.forEach(emailResponse -> {
            Assert.assertNotNull(emailResponse.getId());
        });
    }

    @Test
    public void GetEmails_WrongCustomerId_404NotFound(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .get("/api/customers/0/emails");

        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void DeleteEmails_CustomerIdAndEmailIdInPath_204NoContent(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/"+testCustomer.getId()+"/emails/" + testEmail.getId());

        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    public void DeleteEmail_WrongCustomerId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/0/addresses/" + testEmail.getId());

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void DeleteEmail_WrongEmailId_400BadRequest(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .delete("/api/customers/"+testCustomer.getId()+"/addresses/0");

        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
