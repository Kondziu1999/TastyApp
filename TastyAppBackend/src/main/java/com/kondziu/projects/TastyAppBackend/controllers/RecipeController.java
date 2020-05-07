package com.kondziu.projects.TastyAppBackend.controllers;

import com.kondziu.projects.TastyAppBackend.dto.RecipeOverview;
import com.kondziu.projects.TastyAppBackend.dto.RecipesOverviewWrapper;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import com.kondziu.projects.TastyAppBackend.services.RecipesOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
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

    @GetMapping("/recipes/overview")
    public  ResponseEntity<RecipesOverviewWrapper> getRecipesOverview(){
        return ResponseEntity.ok(recipesOverviewService.getRecipesOverview());
        //return ResponseEntity.ok();
    }

}
