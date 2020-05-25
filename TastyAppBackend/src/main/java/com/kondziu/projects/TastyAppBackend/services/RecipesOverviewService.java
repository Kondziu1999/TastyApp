package com.kondziu.projects.TastyAppBackend.services;

import com.kondziu.projects.TastyAppBackend.dto.*;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.models.User;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import com.kondziu.projects.TastyAppBackend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipesOverviewService {

    private RecipeRepository recipeRepository;
    private UserRepository userRepository;

    @Value("${page.default-page}")
    private int PAGE_DEFAULT;

    @Value("${page.size}")
    private int PAGE_SIZE;

    @Autowired
    public RecipesOverviewService(RecipeRepository recipeRepository, UserRepository userRepository){
        this.recipeRepository=recipeRepository;
        this.userRepository = userRepository;
    }

    public RecipesOverviewWrapper getRecipesOverview(Optional<Integer> optionalPage){
        System.out.println("Przed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //get Content from page
        List<Recipe> recipes = recipeRepository.
                findAll(PageRequest.of( optionalPage.orElse(PAGE_DEFAULT) , PAGE_SIZE))
                .getContent();

//        List<XDD> recipeDtos = recipeRepository.findRecipeDbResult(PageRequest.of(optionalPage.orElse(PAGE_DEFAULT),PAGE_SIZE));
        //System.out.println(recipeDtos.get(0).getName());
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
        System.out.println("PO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        return wrapper;
    }


}
