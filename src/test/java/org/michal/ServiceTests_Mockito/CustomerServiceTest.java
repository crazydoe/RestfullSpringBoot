package org.michal.ServiceTests_Mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.model.CustomerResponse;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.michal.org.michal.webservice.service.AddressService;
import org.michal.org.michal.webservice.service.CustomerServiceBean;
import org.michal.org.michal.webservice.service.EmailService;
import org.michal.org.michal.webservice.service.PhoneNumberService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;

/**
 * Created by michal on 16.12.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private AddressService addressServiceMock;

    @Mock
    private PhoneNumberService phoneNumberServiceMock;

    @InjectMocks
    private CustomerServiceBean customerService;

    @Test
    public void FindAllCustomers_ShouldReturnCollectionOfCustomers(){
        Collection<Customer> expected = new ArrayList<>();
        expected.add(new Customer().setName("test1").setId(1L));
        expected.add(new Customer().setName("test2").setId(2L));

        when(customerRepository.findAll()).thenReturn(expected);

        when(emailServiceMock.findByCustomerId(Mockito.anyLong()))
                .thenReturn(null);
        when(addressServiceMock.findByCustomerId(Mockito.anyLong()))
                .thenReturn(null);
        when(phoneNumberServiceMock.findByCustomerId(Mockito.anyLong()))
                .thenReturn(null);

        Collection<CustomerResponse> actual = customerService.findAll();

        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.size(), expected.size());
    }

    @Test
    public void CreateCustomer_CorrectlyFilledObject_ShouldReturnCustomer(){
        Customer expected = new Customer()
                .setName("test")
                .setGender("test")
                .setSurname("test");

        when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(expected);

        Customer actual = customerService.createCustomer(expected);
        Assert.assertNotNull(actual);
    }

    @Test
    public void CreateCustomer_SpecifiedCustomerId_ShouldReturnNull(){
        Customer expected = new Customer()
                .setId(1L)
                .setName("test")
                .setGender("test")
                .setSurname("test");

        when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(expected);

        Customer actual = customerService.createCustomer(expected);
        Assert.assertNull(actual);
    }

    @Test
    public void CreateCustomer_EmptyCustomer_ShouldReturnNull(){

        when(customerRepository.save(Mockito.any(Customer.class)))
                .thenReturn(Mockito.any(Customer.class));

        Customer actual = customerService.createCustomer(new Customer());
        Assert.assertNull(actual);
    }
}
