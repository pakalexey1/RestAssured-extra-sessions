package com.cydeo.tests.practice2023.day2;

import com.cydeo.tests.practice2023.pojo.Search;
import com.cydeo.tests.practice2023.pojo.Spartan;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class P07_JsonToPojo extends SpartanTestBase{

    /*
{
    "id": 10,
    "name": "Lorenza",
    "gender": "Female",
    "phone": 3312820936
}
 */

    @Test
    public void getSpartan(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 10)
                .when().get("{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();


        System.out.println("First Approach -> Response.path()");

        Spartan spartan = response.as(Spartan.class);
        System.out.println(spartan);
        System.out.println("spartan.getId() = " + spartan.getId());
        System.out.println("spartan.getName() = " + spartan.getName());

        System.out.println("JSON PATH APPROACH");

        JsonPath jsonPath = response.jsonPath();
        Spartan jsonSpartan = jsonPath.getObject("",Spartan.class);
        System.out.println("jsonSpartan = " + jsonSpartan);
        System.out.println("jsonSpartan.getId() = " + jsonSpartan.getId());
        System.out.println("jsonSpartan.getName() = " + jsonSpartan.getName());
    }

    @Test
    public void searchSpartans(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("nameContains", "f")
                .queryParam("gender", "Female")
                .when().get("/search").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON.toString())
                .extract().response();

        System.out.println("First Approach of Getting First Spartan -> Response.path()");

//        response.as("") // can't use this method to get partial response as a POJO class since the method doesn't have a partial path parameter for this.


        System.out.println("Second Approach of Getting First Spartan -> JsonPath");
        JsonPath jsonPath = response.jsonPath();
        Spartan spartan = jsonPath.getObject("content[0]", Spartan.class);
        System.out.println(spartan);
        System.out.println("spartan.getId() = " + spartan.getId());
        System.out.println("spartan.getName() = " + spartan.getName());
    }

    @Test
    public void searchSpartansPojo(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("nameContains", "f")
                .queryParam("gender", "female")
                .when().get("/search").prettyPeek()
                .then().contentType(ContentType.JSON)
                .statusCode(200).extract().response();

        JsonPath jsonPath = response.jsonPath();
        Search jsonSearch = jsonPath.getObject("", Search.class);

        //print totalNumber of elements
        System.out.println("jsonSearch.getTotalElement() = " + jsonSearch.getTotalElement());
/*
        //print how many spartans there are
        List<Spartan> searchContent = jsonSearch.getContent();
        System.out.println("jsonSearch.getContent().size() = " + searchContent.size()); //getContent returns the list of objects.

        //print first spartan info
        System.out.println("searchContent.get(0) = " + searchContent.get(0));

        //print first spartan name
        System.out.println("searchContent.get(0).getName() = " + searchContent.get(0).getName());
*/

        //print how many spartans there are
        List<Spartan> allSpartans = jsonSearch.getAllSpartans();
        for (Spartan eachSpartan : allSpartans) {
            System.out.println(eachSpartan);
        }

        //print second spartan's info
        System.out.println("jsonSearch.getAllSpartans().get(1) = " + allSpartans.get(1));
    }
}

