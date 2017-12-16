package org.michal.RestAssuredTests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/**
 * Created by michal on 16.12.2017.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.err.println(failure.toString());
        }

        System.out.println("Success? : " + result.wasSuccessful());
    }
}
