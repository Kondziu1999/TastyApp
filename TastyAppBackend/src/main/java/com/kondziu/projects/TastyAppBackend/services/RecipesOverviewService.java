package com.kondziu.projects.TastyAppBackend.services;

import com.kondziu.projects.TastyAppBackend.dto.RecipeOverview;
import com.kondziu.projects.TastyAppBackend.dto.RecipesOverviewWrapper;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipesOverviewService {

    private RecipeRepository recipeRepository;

    @Value("${page.default-page}")
    private int PAGE_DEFAULT;

    @Value("${page.size}")
    private int PAGE_SIZE;

    @Autowired
    public RecipesOverviewService(RecipeRepository recipeRepository){
        this.recipeRepository=recipeRepository;
    }

    public RecipesOverviewWrapper getRecipesOverview(Optional<Integer> optionalPage){
        //get Content from page
        List<Recipe> recipes = recipeRepository.
                findAll(PageRequest.of( optionalPage.orElse(PAGE_DEFAULT) , PAGE_SIZE))
                .getContent();

        RecipesOverviewWrapper wrapper=new RecipesOverviewWrapper();

        recipes.
                forEach(recipe -> wrapper.addOverview(
                    new RecipeOverview(
                        recipe.getId(),
                        recipe.getUser().getId().intValue(),
                        recipe.getName(),
                        recipe.getLevel(),
                        recipe.getTime(),
                        recipe.getPortions())));
        return wrapper;
    }


}
