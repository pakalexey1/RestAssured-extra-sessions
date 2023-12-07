package com.cydeo.tests.officehours.day1;

import com.cydeo.utils.TypiCodeTestBase;
import com.sun.source.tree.AssertTree;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.nio.file.Path;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTask1 extends TypiCodeTestBase {
    /**
     Q1:
     - Given accept type is Json
     - When user sends request to https://jsonplaceholder.typicode.com/posts

     -Then print response body

     - And status code is 200
     - And Content - Type is Json
     */
    @Test
            public void task1(){
        Response response = given().log().all().accept(ContentType.JSON)
                .when().get("/posts");

        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK,response.statusCode());

        assertEquals("application/json; charset=utf-8",response.contentType());
    }

    /**
     Q2:
     - Given accept type is Json
     - Path param "id" value is 1
     - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
     - Then status code is 200

     -And json body contains "repellat provident"
     - And header Content - Type is Json
     - And header "X-Powered-By" value is "Express"
     - And header "X-Ratelimit-Limit" value is 1000
     - And header "Age" value is more than 100

     - And header "NEL" value contains "success_fraction"
     */
    @Test
    public void task2(){
        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("id", 1)
                .when().get("/posts/{id}");
        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertTrue(response.asString().contains("repellat provident"));
        assertEquals("application/json; charset=utf-8",response.contentType());
        assertTrue(response.getHeaders().hasHeaderWithName("Date"));
        JsonPath jsonPath = response.jsonPath();
        assertEquals("Express",response.getHeader("X-Powered-By"));
        assertEquals("1000",response.getHeader("X-Ratelimit-Limit"));
        String age = response.getHeader("Age");
        System.out.println(response.getHeader("Age"));
        assertTrue(Integer.parseInt(age)>=100);

        Integer ageWithValueOf = Integer.valueOf(response.getHeader("Age"));
        System.out.println(response.getHeader("NEL"));
        assertTrue(response.getHeader("NEL").contains("success_fraction)"));

    }


    /**
     Give accept type is Json
     Path paramID value is 12345
     When user send request to typicode
     Then the status code is 404
     And the content type is Json
     */
    @Test
    public void task3(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 12345)
                .when().get("/posts/{id}");

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());

        assertEquals("{}",response.asString());
    }


    /**Give accept type is Json
    Path aram "id" is 2
     When user sends request to https://jsonplaceholder.typicode.com/posts/{id}/comments
     then teh status code is 200
     and header content is Json
     And json body contains "Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"
     */
    @Test
    public void task04(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when().get("/posts/{id}/comments");

        response.prettyPrint();

        // then teh status code is 200
        assertEquals(200,response.statusCode());


        assertTrue(response.asString().contains("Presley.Mueller@myrl.com"));
        assertTrue(response.asString().contains("Dallas@ole.me"));
        assertTrue(response.asString().contains("Mallory_Kunze@marie.org"));
        assertTrue(response.body().asString().contains("Mallory_Kunze@marie.org"));

//        response.path
//        Presley
        String email = response.path("[0].email");
        System.out.println(email);

//        Dallas
        email = response.path("[1].email");

//        json path
        JsonPath jsonPath = response.jsonPath();
        System.out.println("jsonPath.getString(\"[0].email\") = " + jsonPath.getString("email[0]"));//Presley

        System.out.println("jsonPath.getString(\"email[1]\") = " + jsonPath.getString("email[1]"));//Dallas

        System.out.println("jsonPath.getString(\"email[2]\") = " + jsonPath.getString("email[2]"));//Mallory

        //List of emails
        List<String> allEmailsWithPath = response.path("email");
        System.out.println(allEmailsWithPath);
        System.out.println("jsonPath.getList(\"email\") = " + jsonPath.getList("email"));

    }

    /**
    //- Given accept type is Json
    //- Query Param "postId" value is 1
    //- When user sends request to  https://jsonplaceholder.typicode.com/comments
    //- Then status code is 200
    //
    //- And header Content - Type is Json
    //
    //- And header "Connection" value is "keep-alive"
    //- And json body contains "Lew@alysha.tv"
*/
    @Test
    public void task05(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("postId", 1)
                .when().get("/comments");

        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertEquals("application/json; charset=utf-8",response.contentType());

        assertEquals("keep-alive", response.getHeader("Connection"));
        assertTrue(response.asString().contains("Lew@alysha.tv"));

    }
}
