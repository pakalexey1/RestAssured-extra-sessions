package com.cydeo.tests.practice2023.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Search {
    @JsonProperty("content")
    private List<Spartan> allSpartans; //corresponds to the content key in the DB
    private int totalElement;

}
