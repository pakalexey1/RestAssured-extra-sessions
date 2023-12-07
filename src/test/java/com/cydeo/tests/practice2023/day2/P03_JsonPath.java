package com.cydeo.tests.practice2023.day2;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class P03_JsonPath extends HrTestBase{



    @Test
    public void getLocations(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/locations");

        assertEquals(200,response.statusCode());

        assertEquals(ContentType.JSON.toString(),response.contentType());

        JsonPath jsonPath = response.jsonPath();

        jsonPath.getString("items[1].city");

        jsonPath.getString("items[-1].city");

        List<String> allCountryIDs = jsonPath.getList("items.country_id");

        List<String> allCityUK = jsonPath.getList("items.findAll {it.country_id=='UK'}.city");



    }
}
