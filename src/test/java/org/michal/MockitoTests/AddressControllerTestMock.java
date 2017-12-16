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
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Collection;
import static org.mockito.Mockito.when;

/**
 * Created by michal on 14.12.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class AddressControllerTestMock {

    @Mock
    private CustomerRepository customerRepositoryMocked;

    @Mock
    private AddressRepository addressRepositoryMocked;

    @InjectMocks
    private AddressServiceBean addressService;

    @Before
    public void setup(){

        /* CUSTOMER REPOSITORY MOCK */

        when(customerRepositoryMocked.findOne(1L)).thenReturn(
                new Customer().setId(1L).setName("test").setSurname("test").setGender("test"));
        when(customerRepositoryMocked.findOne(2L)).thenReturn(
                new Customer().setId(2L).setName("test").setSurname("test").setGender("test"));

        when(customerRepositoryMocked.exists(1L)).thenReturn(true);
        when(customerRepositoryMocked.exists(2L)).thenReturn(true);
        when(customerRepositoryMocked.exists(0L)).thenReturn(false);

        /* ADDRESS REPOSITORY MOCK */

        Collection<Address> addresses = new ArrayList<>();
        addresses.add(new Address().setAddress("test").setCustomerId(1L).setId(1L));
        addresses.add(new Address().setAddress("test").setCustomerId(1L).setId(2L));
        addresses.add(new Address().setAddress("test").setCustomerId(2L).setId(3L));

        when(addressRepositoryMocked.findAll()).thenReturn(addresses);
        when(addressRepositoryMocked.exists(1L)).thenReturn(true);
        when(addressRepositoryMocked.exists(2L)).thenReturn(true);
        when(addressRepositoryMocked.exists(3L)).thenReturn(true);
        when(addressRepositoryMocked.exists(0L)).thenReturn(false);

    }

    @Test
    public void GetAddresses_SpecifiedCustomer_CollectionOfCustomers() throws Exception{
        Assert.assertEquals(addressService.findByCustomerId(1L).size(), 2);
        Assert.assertEquals(addressService.findByCustomerId(2L).size(), 1);
    }

    @Test
    public void GetAddresses_WronglySpecifiedCustomer_EmptyCollection(){
        Assert.assertNull(addressService.findByCustomerId(0L));
    }

    @Test
    public void DeleteAddress_SpecifiedCustomerAndAddressId_ReturnedTrue(){
        Assert.assertEquals(addressService
                .deleteAddressByCustomerId(1L,1L), true);
    }

    @Test
    public void DeleteAddress_WronglySpecifiedCustomerId_ReturnedFalse(){
        Assert.assertEquals(addressService
                .deleteAddressByCustomerId(0L,1L), false);
    }

    @Test
    public void DeleteAddress_WronglySpecifiedAddressId_ReturnedFalse(){
        Assert.assertEquals(addressService
                .deleteAddressByCustomerId(1L,0L), false);
    }

    @Test
    public void PostAddress_SpecifiedCustomerId_CreatedAddressObject(){
        Address expected = new Address().setAddress("testSave").setCustomerId(2L).setId(4L);
        when(addressRepositoryMocked.save(Mockito.any(Address.class))).thenReturn(expected);

        Address actual = addressService.createAddressForCustomerId(2L, expected);
        Assert.assertSame(actual, expected);
    }

    @Test
    public void PostAddress_WrongCustomerId_ShouldReturnNull(){
        Address address = new Address().setAddress("testSave");

        when(addressRepositoryMocked
                .save(Mockito.any(Address.class)))
                .thenReturn(Mockito.any(Address.class));

        Assert.assertNull(addressService.
                createAddressForCustomerId(0L, address));
    }

    @Test
    public void PostAddress_EmptyBody_ShouldReturnNull(){
        when(addressRepositoryMocked
                .save(Mockito.any(Address.class)))
                .thenReturn(Mockito.any(Address.class));

        Assert.assertNull(addressService.createAddressForCustomerId(0L, new Address()));
    }

    @Test
    public void PutAddress_SpecifiedCustomerIdAndAddressId_ShouldReturnUpdatedObject() {
        Address expected = new Address().setAddress("testSave").setId(1L);

        when(addressRepositoryMocked
                .save(Mockito.any(Address.class)))
                .thenReturn(expected);

        Address actual = addressService.updateAddressByCustomerId(1L, expected);
        Assert.assertEquals(actual.getAddress(), expected.getAddress());
    }


}
