package com.kondziu.projects.TastyAppBackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecipeOverview {

    private Integer id;
    private Integer userId;
    private String name;
    private String level;
    private String time;
    private String portions;

}
