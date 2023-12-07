package com.cydeo.tests.practice2023.day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class P05_SpartanTest extends SpartanTestBase {

    /*
    Send a request to GET /spartans/search
    Query Parameters values are
    gender -> female
    name contains -> f
    Log everything
    Verify following:
        status code is 200
        Content type is application/json
        number of elements is 4
        jsonArray size is 4
        Names hasItem "Alfy"
        Every gender is Female
     */

    @Test
    public void searchSpartans(){

        given().log().all()
                .queryParam("gender","Female")
                .queryParam("nameContains","f")
                .when().get("/search").prettyPeek()
                .then()
                .log().ifValidationFails()//if any validation fails in then() using Hamcrest it will log the details
                .contentType(ContentType.JSON)
                .body("totalElement",is(4))
                .body("content",hasSize(4))
                .body("content.name",hasItem("Alfy"))
                .body("content.gender",everyItem(is("Female")));

    }
}
