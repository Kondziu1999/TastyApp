package com.kondziu.projects.TastyAppBackend.mappers;

import com.kondziu.projects.TastyAppBackend.dto.RecipeDto;
import com.kondziu.projects.TastyAppBackend.models.Recipe;

public class RecipeToDtoMapper {
    private RecipeToDtoMapper(){}


    public static RecipeDto recipeToDto(Recipe recipe){
        return RecipeDto
            .builder()
            .recipeId(recipe.getId())
            .userId(recipe.getUser().getId().intValue())
            .name(recipe.getName())
            .level(recipe.getLevel())
            .time(recipe.getTime())
            .portions(recipe.getPortions())
            .ingredients(recipe.getIngredients())
            .steps(recipe.getSteps())
            .description(recipe.getDescription())
            .build();
    }

}
