package com.cydeo.tests.practice2023.day2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class P01_Contains extends HrTestBase{
    @Test
    public void getSingleRegion(){

        Response response= given().accept(ContentType.JSON)
                .pathParam("id",2)
                .when().get("/regions/{id}").prettyPeek();

        //response is 200
        assertEquals(200,response.statusCode());

        //content type is JSON
        assertEquals(ContentType.JSON,response.getContentType());

        //body contains Americas
        assertTrue(response.asString().contains("Americas"));

    }

}
