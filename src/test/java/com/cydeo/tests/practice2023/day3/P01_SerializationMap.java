package com.cydeo.tests.practice2023.day3;
import com.cydeo.tests.practice2023.day2.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class P01_SerializationMap extends SpartanTestBase {

    static int spartanID;

    @Order(1)
    @Test
    public void postSpartan(){

        //verify success message "spartan is born"
        //extract id info from response

        Map<String, Object> spartanMap = new LinkedHashMap<>();
        spartanMap.put("name","Rest POST");
        spartanMap.put("gender","Female");
        spartanMap.put("phone",12312341212l);
        System.out.println(spartanMap);

        spartanID = given().accept(ContentType.JSON) //I want to get a response data in JSON Format
                .contentType(ContentType.JSON) //I am sending data in tJSON format
                .body(spartanMap) //serialization is happening here by converting an object (map) into JSON
                .when().post("").prettyPeek()
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("success", is("A Spartan is Born!"))
                .extract().jsonPath().getInt("data.id");

        System.out.println("Generated id: " + spartanID);
    }
    @Order(2)
    @Test
    public void putSpartan(){
        Map<String, Object> spartanMap = new LinkedHashMap<>();
        spartanMap.put("name","Rest PUT");
        spartanMap.put("gender","Female");
        spartanMap.put("phone",12312341212l);

        given().contentType(ContentType.JSON)
                .pathParam("id",spartanID)
                .body(spartanMap)
                .when()
                .put("/{id}")
                .then().statusCode(204);

        System.out.println(spartanID + " is updated");

    }
    @Order(3)
    @Test
    public void patchSpartan(){
        Map<String, Object> spartanMap = new LinkedHashMap<>();
        spartanMap.put("name","PATCH REST");

        given().contentType(ContentType.JSON)
                .pathParam("id",spartanID)
                .body(spartanMap)
                .when()
                .patch("/{id}")
                .then().statusCode(204);

        System.out.println(spartanID + " is partially updated");
    }

    @Order(4)
    @Test
    public void deleteSpartan(){

        given().pathParam("id",spartanID)
                .when().delete("{id}")
                .then().statusCode(204);
        System.out.println("147 is deleted");

        given().pathParam("id",spartanID)
                .when().get("{id}").prettyPeek()
                .then().statusCode(404);
        System.out.println("147 is not found");
    }


}
