package com.cydeo.tests.practice2023.day2;
import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class HrTestBase {

    @BeforeAll
    public static void init(){
        baseURI="http://54.173.31.211:1000";
        basePath="/ords/hr";
    }

    @AfterAll
    public static void destroy(){
        reset();
    }
}
