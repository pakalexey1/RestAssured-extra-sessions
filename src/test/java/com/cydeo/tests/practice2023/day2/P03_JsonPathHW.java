package com.cydeo.tests.practice2023.day2;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class P03_JsonPathHW extends HrTestBase{

    @Test
    public void getEmployees(){

        /*
        When accept type is application/JSON
        And user sends get request to /employees
        Then response status must be 200
        and get all the employees first_name who is making salary more than 15000
         */

        Response response = given().accept(ContentType.JSON)
                .when().get("/employees");

        assertEquals(200,response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        jsonPath.getList("items.findAll {it.salary>15000}.first_name");

    }

}
