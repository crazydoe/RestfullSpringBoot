package org.michal.ServiceTests_Mockito;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.michal.org.michal.webservice.model.Customer;
import org.michal.org.michal.webservice.model.PhoneNumber;
import org.michal.org.michal.webservice.repository.CustomerRepository;
import org.michal.org.michal.webservice.repository.PhoneNumberRepository;
import org.michal.org.michal.webservice.service.PhoneNumberServiceBean;
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
public class PhoneNumberServiceTest {

    @Mock
    private CustomerRepository customerRepositoryMocked;

    @Mock
    private PhoneNumberRepository numberRepositoryMocked;

    @InjectMocks
    private PhoneNumberServiceBean numberService;

    @Before
    public void setup(){

//         CUSTOMER REPOSITORY MOCK

        when(customerRepositoryMocked.findOne(1L)).thenReturn(
                new Customer().setId(1L).setName("test").setSurname("test").setGender("test"));
        when(customerRepositoryMocked.findOne(2L)).thenReturn(
                new Customer().setId(2L).setName("test").setSurname("test").setGender("test"));

        when(customerRepositoryMocked.exists(1L)).thenReturn(true);
        when(customerRepositoryMocked.exists(2L)).thenReturn(true);
        when(customerRepositoryMocked.exists(0L)).thenReturn(false);

//        PHONE NUMBER REPOSITORY MOCK

        Collection<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber().setPhoneNumber("123").setCustomerId(1L).setId(1L));
        numbers.add(new PhoneNumber().setPhoneNumber("234").setCustomerId(1L).setId(2L));
        numbers.add(new PhoneNumber().setPhoneNumber("345").setCustomerId(2L).setId(3L));

        when(numberRepositoryMocked.findAll()).thenReturn(numbers);
        when(numberRepositoryMocked.exists(1L)).thenReturn(true);
        when(numberRepositoryMocked.exists(2L)).thenReturn(true);
        when(numberRepositoryMocked.exists(3L)).thenReturn(true);
        when(numberRepositoryMocked.exists(0L)).thenReturn(false);
    }

    @Test
    public void FindByCustomerId_SpecifiedCustomer_CollectionOfPhoneNumbers() throws Exception{
        Assert.assertEquals(numberService.findByCustomerId(1L).size(), 2);
        Assert.assertEquals(numberService.findByCustomerId(2L).size(), 1);
    }

    @Test
    public void FindByCustomerId_WronglySpecifiedCustomer_EmptyCollection(){
        Assert.assertNull(numberService.findByCustomerId(0L));
    }

    @Test
    public void DeleteNumberByCustomerId_SpecifiedCustomerAndPhoneNumberId_ReturnedTrue(){
        Assert.assertEquals(numberService
                .deleteNumberByCustomerId(1L,1L), true);
    }

    @Test
    public void DeleteNumberByCustomerId_WronglySpecifiedCustomerId_ReturnedFalse(){
        Assert.assertEquals(numberService
                .deleteNumberByCustomerId(0L,1L), false);
    }

    @Test
    public void DeleteNumberByCustomerId_WronglySpecifiedPhoneNumberId_ReturnedFalse(){
        Assert.assertEquals(numberService
                .deleteNumberByCustomerId(1L,0L), false);
    }

    @Test
    public void CreateNumberForCustomerId_SpecifiedCustomerId_CreatedPhoneNumberObject(){
        PhoneNumber expected = new PhoneNumber().setPhoneNumber("987").setCustomerId(2L).setId(4L);
        when(numberRepositoryMocked.save(Mockito.any(PhoneNumber.class))).thenReturn(expected);

        PhoneNumber actual = numberService.createNumberForCustomerId(2L, expected);
        Assert.assertSame(actual, expected);
    }

    @Test
    public void CreateNumberForCustomerId_WrongCustomerId_ShouldReturnNull(){
        PhoneNumber address = new PhoneNumber().setPhoneNumber("987");

        when(numberRepositoryMocked
                .save(Mockito.any(PhoneNumber.class)))
                .thenReturn(Mockito.any(PhoneNumber.class));

        Assert.assertNull(numberService.
                createNumberForCustomerId(0L, address));
    }

    @Test
    public void CreateNumberForCustomerId_EmptyObject_ShouldReturnNull(){
        when(numberRepositoryMocked
                .save(Mockito.any(PhoneNumber.class)))
                .thenReturn(Mockito.any(PhoneNumber.class));

        Assert.assertNull(numberService.createNumberForCustomerId(0L, new PhoneNumber()));
    }

    @Test
    public void UpdateNumberByCustomerId_SpecifiedCustomerIdAndPhoneNumberId_ShouldReturnUpdatedObject() {
        PhoneNumber expected = new PhoneNumber().setPhoneNumber("987").setId(1L);

        when(numberRepositoryMocked
                .save(Mockito.any(PhoneNumber.class)))
                .thenReturn(expected);

        PhoneNumber actual = numberService.updateNumberByCustomerId(1L, expected);
        Assert.assertEquals(actual.getPhoneNumber(), expected.getPhoneNumber());
    }

    @Test
    public void UpdateNumberByCustomerId_WrongCustomerId_ShouldReturnNull() {
        PhoneNumber given = new PhoneNumber().setPhoneNumber("123").setId(1L);

        when(numberRepositoryMocked
                .save(Mockito.any(PhoneNumber.class)))
                .thenReturn(Mockito.any(PhoneNumber.class));

        PhoneNumber actual = numberService.updateNumberByCustomerId(0L, given);
        Assert.assertNull(actual);
    }

    @Test
    public void UpdateNumberByCustomerId_EmptyObject_ShouldReturnNull() {
        when(numberRepositoryMocked
                .save(Mockito.any(PhoneNumber.class)))
                .thenReturn(Mockito.any(PhoneNumber.class));

        PhoneNumber actual = numberService.updateNumberByCustomerId(1L, new PhoneNumber());
        Assert.assertNull(actual);
    }

}
