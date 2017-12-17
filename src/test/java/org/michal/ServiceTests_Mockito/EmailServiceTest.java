package org.michal.ServiceTests_Mockito;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.hibernate.validator.constraints.Email;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.model.EmailAddress;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.michal.org.michal.webservice.repository.EmailRepository;
import org.michal.org.michal.webservice.service.EmailServiceBean;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;

/**
 * Created by michal on 17.12.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @Mock
    private CustomerRepository customerRepositoryMocked;

    @Mock
    private EmailRepository emailRepository;

    @InjectMocks
    private EmailServiceBean emailService;

    @Before
    public void setup() {

//         CUSTOMER REPOSITORY MOCK

        when(customerRepositoryMocked.findOne(1L)).thenReturn(
                new Customer().setId(1L).setName("test").setSurname("test").setGender("test"));
        when(customerRepositoryMocked.findOne(2L)).thenReturn(
                new Customer().setId(2L).setName("test").setSurname("test").setGender("test"));

        when(customerRepositoryMocked.exists(1L)).thenReturn(true);
        when(customerRepositoryMocked.exists(2L)).thenReturn(true);
        when(customerRepositoryMocked.exists(0L)).thenReturn(false);

//        EMAIL REPOSITORY MOCK

        Collection<EmailAddress> emails = new ArrayList<>();
        emails.add(new EmailAddress().setEmailAddress("test@1.com").setCustomerId(1L).setId(1L));
        emails.add(new EmailAddress().setEmailAddress("test@2.com").setCustomerId(1L).setId(2L));
        emails.add(new EmailAddress().setEmailAddress("test@3.com").setCustomerId(2L).setId(3L));

        when(emailRepository.findAll()).thenReturn(emails);
        when(emailRepository.exists(1L)).thenReturn(true);
        when(emailRepository.exists(2L)).thenReturn(true);
        when(emailRepository.exists(3L)).thenReturn(true);
        when(emailRepository.exists(0L)).thenReturn(false);
    }

    @Test
    public void FindByCustomerId_SpecifiedCustomer_CollectionOfEmails() throws Exception{
        Assert.assertEquals(emailService.findByCustomerId(1L).size(), 2);
        Assert.assertEquals(emailService.findByCustomerId(2L).size(), 1);
    }

    @Test
    public void FindByCustomerId_WronglySpecifiedCustomer_EmptyCollection(){
        Assert.assertNull(emailService.findByCustomerId(0L));
    }

    @Test
    public void DeleteEmailByCustomerId_SpecifiedCustomerAndEmailId_ReturnedTrue(){
        Assert.assertEquals(emailService
                .deleteEmailByCustomerId(1L,1L), true);
    }

    @Test
    public void DeleteEmailByCustomerId_WronglySpecifiedCustomerId_ReturnedFalse(){
        Assert.assertEquals(emailService
                .deleteEmailByCustomerId(0L,1L), false);
    }

    @Test
    public void DeleteEmailByCustomerId_WronglySpecifiedEmailId_ReturnedFalse(){
        Assert.assertEquals(emailService
                .deleteEmailByCustomerId(1L,0L), false);
    }

    @Test
    public void CreateEmailForCustomerId_SpecifiedCustomerId_CreatedEmailObject(){
        EmailAddress expected = new EmailAddress().setEmailAddress("set@set").setCustomerId(2L).setId(4L);
        when(emailRepository.save(Mockito.any(EmailAddress.class))).thenReturn(expected);

        EmailAddress actual = emailService.createEmailForCustomerId(2L, expected);
        Assert.assertSame(actual, expected);
    }

    @Test
    public void CreateEmailForCustomerId_WrongCustomerId_ShouldReturnNull(){
        EmailAddress address = new EmailAddress().setEmailAddress("test@test");

        when(emailRepository
                .save(Mockito.any(EmailAddress.class)))
                .thenReturn(Mockito.any(EmailAddress.class));

        Assert.assertNull(emailService.
                createEmailForCustomerId(0L, address));
    }

    @Test
    public void CreateEmailForCustomerId_EmptyObject_ShouldReturnNull(){
        when(emailRepository
                .save(Mockito.any(EmailAddress.class)))
                .thenReturn(Mockito.any(EmailAddress.class));

        Assert.assertNull(emailService.createEmailForCustomerId(0L, new EmailAddress()));
    }

    @Test
    public void UpdateEmailByCustomerId_SpecifiedCustomerIdAndEmailId_ShouldReturnUpdatedObject() {
        EmailAddress expected = new EmailAddress().setEmailAddress("test@test").setId(1L);

        when(emailRepository
                .save(Mockito.any(EmailAddress.class)))
                .thenReturn(expected);

        EmailAddress actual = emailService.updateEmailByCustomerId(1L, expected);
        Assert.assertEquals(actual.getEmailAddress(), expected.getEmailAddress());
    }

    @Test
    public void UpdateEmailByCustomerId_WrongCustomerId_ShouldReturnNull() {
        EmailAddress given = new EmailAddress().setEmailAddress("test@test").setId(1L);

        when(emailRepository
                .save(Mockito.any(EmailAddress.class)))
                .thenReturn(Mockito.any(EmailAddress.class));

        EmailAddress actual = emailService.updateEmailByCustomerId(0L, given);
        Assert.assertNull(actual);
    }

    @Test
    public void UpdateEmailByCustomerId_EmptyObject_ShouldReturnNull() {
        when(emailRepository
                .save(Mockito.any(EmailAddress.class)))
                .thenReturn(Mockito.any(EmailAddress.class));

        EmailAddress actual = emailService.updateEmailByCustomerId(1L, new EmailAddress());
        Assert.assertNull(actual);
    }


}
