package com.kondziu.projects.TastyAppBackend.dto;


import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {

    private int recipeId;
    private int userId;
    private String name;
    private String level;
    private String time;
    private String portions;
    private List<String> ingredients=new ArrayList<>();
    private List<String> steps=new ArrayList<>();
    private String description;
}
