package org.michal.ControllerTests_RestAssured;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by michal on 16.12.2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerControllerTest.class,
        AddressControllerTest.class,
        EmailControllerTest.class,
        PhoneNumberControllerTest.class
})
public class TestSuite {
}
