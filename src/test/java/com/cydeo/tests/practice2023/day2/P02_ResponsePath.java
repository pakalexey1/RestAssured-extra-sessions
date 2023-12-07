package com.cydeo.tests.practice2023.day2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P02_ResponsePath extends HrTestBase {

    @Test
    public void getSingleRegion(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id",2)
                .when().get("/regions/{id}").prettyPeek();

        //response status code must be 200
        assertEquals(200,response.statusCode());

        //region_name is americas
        assertEquals("Americas", response.path("region_name"));

        //region_id is 2
        assertTrue(response.path("region_id").equals(2));

        //print all the links from the response
        List<Map<String,String>> links = response.path("links");
        for (Map<String, String> eachLink: links){
            System.out.println(eachLink.get("rel"));
            System.out.println(eachLink.get("href"));
        }

        //Store all of the links
        List<String> allHref =  response.path("links.href");
        System.out.println("All hrefs: " + allHref);
        }

    @ParameterizedTest
    @CsvFileSource(resources = "/regions.csv",numLinesToSkip = 1 )
    public void parameterizedTest(int id, String regionName){

        System.out.println(id+"---->" + regionName);

        Response response = given().accept(ContentType.JSON)
                .pathParam("id",id)
                .when().get("/regions/{id}" );

        //response status code must be 200
        assertEquals(200, response.statusCode());

        //region_name
        assertEquals(regionName,response.path("region_name"));

        //region_id
        assertEquals(id,(Integer) response.path("region_id"));

        }
    }

