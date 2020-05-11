package com.kondziu.projects.TastyAppBackend.controllers;


import com.kondziu.projects.TastyAppBackend.dto.RecipesOverviewWrapper;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import com.kondziu.projects.TastyAppBackend.services.RecipesOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {
    private RecipeRepository recipeRepository;
    private RecipesOverviewService recipesOverviewService;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository,RecipesOverviewService recipesOverviewService){
        this.recipeRepository=recipeRepository;
        this.recipesOverviewService=recipesOverviewService;
    }

    @PostMapping("/recipes")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe){
        Recipe response=recipeRepository.save(recipe);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Integer id){
        return  ResponseEntity.of(recipeRepository.findById(id));
    }

    @GetMapping(value = {"/recipes/overview","/recipes/overview/{optionalPage}"})
    public  ResponseEntity<RecipesOverviewWrapper> getRecipesOverview(@PathVariable Optional<Integer> optionalPage){
        return ResponseEntity.ok(recipesOverviewService.getRecipesOverview(optionalPage));
    }

}
