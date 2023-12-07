package com.cydeo.tests.practice2023.day2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public abstract class SpartanTestBase {

    @BeforeAll
    public static void init(){
        baseURI="http://54.173.31.211:8000";
        basePath="/api/spartans";
    }

    @AfterAll
    public static void destroy(){
        reset();
    }
}
