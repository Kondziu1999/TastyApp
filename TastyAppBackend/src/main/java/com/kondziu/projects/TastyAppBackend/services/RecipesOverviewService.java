package com.kondziu.projects.TastyAppBackend.services;

import com.kondziu.projects.TastyAppBackend.dto.RecipeOverview;
import com.kondziu.projects.TastyAppBackend.dto.RecipesOverviewWrapper;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipesOverviewService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipesOverviewService(RecipeRepository recipeRepository){
        this.recipeRepository=recipeRepository;
    }

    public RecipesOverviewWrapper getRecipesOverview(){
        List<Recipe> recipes=recipeRepository.findAll();
        RecipesOverviewWrapper wrapper=new RecipesOverviewWrapper();

        recipes.
                forEach(recipe -> wrapper.addOverview(
                                    new RecipeOverview(
                                            recipe.getId(),
                                            recipe.getName(),
                                            recipe.getLevel(),
                                            recipe.getTime(),
                                            recipe.getPortions())));
        return wrapper;
    }


}
