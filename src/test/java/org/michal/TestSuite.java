package org.michal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by michal on 16.12.2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerResponseTest.class,
        AddressResponseTest.class,
        EmailAddressResponseTest.class,
        PhoneNumberResponseTest.class
})
public class TestSuite {
}
