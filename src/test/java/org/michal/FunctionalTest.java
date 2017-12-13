package org.michal;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

/**
 * Created by michal on 13.12.2017.
 */
public class FunctionalTest {

    @BeforeTest
    public static void setup(){
        String port = System.getProperty("server.port");

        if (port == null) RestAssured.port = 8080;
        else RestAssured.port = Integer.valueOf(port);

        String basePath = System.getProperty("server.base");

        if(basePath==null) basePath = "/";

        RestAssured.basePath = basePath;
        String baseHost = System.getProperty("server.host");

        if(baseHost==null) baseHost = "http://localhost/api";

        RestAssured.baseURI = baseHost;
    }
}
