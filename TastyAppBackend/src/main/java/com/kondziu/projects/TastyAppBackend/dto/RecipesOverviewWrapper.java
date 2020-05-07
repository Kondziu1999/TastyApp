package com.kondziu.projects.TastyAppBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipesOverviewWrapper {

    private List<RecipeOverview> recipesOverview= new ArrayList<>();

    public void addOverview(RecipeOverview recipeOverview){
        this.recipesOverview.add(recipeOverview);
    }

    public void removeOverview(RecipeOverview recipeOverview){
        this.recipesOverview.remove(recipeOverview);
    }
}
