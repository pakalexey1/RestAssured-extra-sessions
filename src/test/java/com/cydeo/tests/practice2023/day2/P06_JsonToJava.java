package com.cydeo.tests.practice2023.day2;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class P06_JsonToJava extends SpartanTestBase{

    /*
    Given accept type is application/json
    And Path param id = 10
    When I send GET request to /api/spartans
    Then status code is 200
    And content type is JSON
    And spartan data matching:
    id>10
    name>Lorenza
    gender>Female
    phone>3312820936
     */

    @Test
    public void getSingleSpartan(){

      Response response = given().accept(ContentType.JSON)
                .pathParam("id",10)
                .when().get("/{id}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("First Approach -> Response.path()");
//        Map <String,Object> spartanMap = response.as(Map.class);
        //as() -> method does deserialization and if JSON needs to be converted to a Map using as() method, it requires Jackson or GSON Databing/Objectmapper library
        Map<String,Object> spartanMap = response.path("");
        System.out.println("spartanMap= " +spartanMap);

        int id = (int)spartanMap.get("id");
        String name = (String)spartanMap.get("name");
        System.out.println("id " + id);
        System.out.println("name " + name);

        System.out.println("Second Approach -> JsonPath");
        JsonPath jsonPath = response.jsonPath();

        Map<String, Object> spMap = jsonPath.getMap("");
        int id1 = (int) spMap.get("id");
        String name1= (String) spMap.get("name");

        System.out.println(id1);
        System.out.println(name1);

    }

    @Test
  public void getAllSpartans(){

      Response response = given().accept(ContentType.JSON)
              .when().get("")
              .then().statusCode(200)
              .contentType(ContentType.JSON)
              .extract().response();

      System.out.println("First Approach -> Response.path()");
      List<Map<String,Object>> allSpartans = response.path("");

      //print out all spartans

      for (Map<String, Object> eachSpartan : allSpartans){
        System.out.println(eachSpartan);
      }

      //find the first spartan's info

      System.out.println("First Spartan: " + allSpartans.get(0));

      //find the first spartan's name

      Map<String, Object> firstSpartan = allSpartans.get(0);
      String name = (String) firstSpartan.get("name");
      //OR:
      System.out.println("allSpartans.get(0).get(\"name\") = " + allSpartans.get(0).get("name"));

      System.out.println("Second Approach -> JsonPath");

      JsonPath jsonPath = response.jsonPath();
      List<Map<String,Object>> jsonSpartans = jsonPath.getList("");
      //print out all spartans

      for (Map<String, Object> eachSpartan : jsonSpartans){
        System.out.println(eachSpartan);
      }

      //find the first spartan's info

      System.out.println("jsonSpartans.get(0) = " + jsonSpartans.get(0));

      //find the first spartan's name
      System.out.println("jsonSpartans.get(0).get(\"name\") = " + jsonSpartans.get(0).get("name"));


    }
}
