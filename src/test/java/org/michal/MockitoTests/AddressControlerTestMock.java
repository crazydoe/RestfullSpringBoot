package org.michal.MockitoTests;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.michal.org.michal.webservice.model.Address;
import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.repository.AddressRepository;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.michal.org.michal.webservice.service.AddressServiceBean;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Collection;
import static org.mockito.Mockito.when;

/**
 * Created by michal on 14.12.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class AddressControlerTestMock {

    @Mock
    private CustomerRepository customerRepositoryMocked;

    @Mock
    private AddressRepository addressRepositoryMocked;

    @InjectMocks
    private AddressServiceBean addressService;

    @Before
    public void setup(){

        /* CUSTOMER REPOSITORY SETUP */

        when(customerRepositoryMocked.findOne(1L)).thenReturn(
                new Customer().setId(1L).setName("test").setSurname("test").setGender("test")
        );
        when(customerRepositoryMocked.findOne(2L)).thenReturn(
                new Customer().setId(2L).setName("test").setSurname("test").setGender("test")
        );

        when(customerRepositoryMocked.exists(1L)).thenReturn(true);
        when(customerRepositoryMocked.exists(2L)).thenReturn(true);
        when(customerRepositoryMocked.exists(3L)).thenReturn(false);

        /* ADDRESS REPOSITORY SETUP */

        Collection<Address> addresses = new ArrayList<>();
        addresses.add(new Address().setAddress("test").setCustomerId(1L).setId(1L));
        addresses.add(new Address().setAddress("test").setCustomerId(1L).setId(2L));
        addresses.add(new Address().setAddress("test").setCustomerId(2L).setId(3L));

        when(addressRepositoryMocked.findAll()).thenReturn(addresses);
        when(addressRepositoryMocked.exists(1L)).thenReturn(true);
        when(addressRepositoryMocked.exists(2L)).thenReturn(true);
        when(addressRepositoryMocked.exists(3L)).thenReturn(true);
        when(addressRepositoryMocked.exists(4L)).thenReturn(false);

    }

    @Test
    public void GetAddresses_ForSpecifiedCustomer() throws Exception{
        Assert.assertEquals(addressService.findByCustomerId(1L).size(), 2);
        Assert.assertEquals(addressService.findByCustomerId(2L).size(), 1);
    }

    @Test
    public void GetAddresses_ForWronglySpecifiedCustomer(){
        Assert.assertNotEquals(addressService.findByCustomerId(3L).size(), 1);
    }

    @Test
    public void DeleteAddress_ForSpecifiedCustomerAndAddressId(){
        Assert.assertEquals(addressService.deleteAddressByCustomerId(1L,1L), true);
    }

    @Test
    public void DeleteAddress_ForWronglySpecifiedCustomerId(){
        Assert.assertEquals(addressService.deleteAddressByCustomerId(3L,1L), false);
    }

    @Test
    public void DeleteAddress_ForWronglySpecifiedAddressId(){
        Assert.assertEquals(addressService.deleteAddressByCustomerId(1L,4L), false);
    }



}
