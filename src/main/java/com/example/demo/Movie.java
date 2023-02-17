package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Movie {
    private Integer id;
    private String title;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
}
