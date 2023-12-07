package com.cydeo.tests.officehours.day1;

import com.cydeo.utils.HrApiTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTask2 extends HrApiTestBase {
    @Test
    public void task1(){

/**
 Q1:
 - Given accept type is Json
 - Path param value- US
 - When users sends request to /countries
 - Then status code is 200
 - And Content - Type is Json
 - And country_id is US
 - And Country_name is United States of America
 - And Region_id is 2
 */

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country_id","US")
                .when().get("/countries/{country_id}");

        assertEquals(200,response.getStatusCode());

        response.prettyPrint();

        assertEquals(ContentType.JSON.toString(),response.getContentType());

        //convert to JsonPath

        JsonPath jsonPath = response.jsonPath();

        assertEquals("US",jsonPath.getString("country_id"));
        assertEquals("United States of America",jsonPath.getString("country_name"));
        assertEquals(2, jsonPath.getInt("region_id"));


    }
}
