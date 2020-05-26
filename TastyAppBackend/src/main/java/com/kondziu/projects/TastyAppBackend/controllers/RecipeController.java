package com.kondziu.projects.TastyAppBackend.controllers;


import com.kondziu.projects.TastyAppBackend.dto.RecipeDto;
import com.kondziu.projects.TastyAppBackend.dto.RecipesOverviewWrapper;
import com.kondziu.projects.TastyAppBackend.exceptions.RecipeNotFoundException;
import com.kondziu.projects.TastyAppBackend.exceptions.UserNotFoundException;
import com.kondziu.projects.TastyAppBackend.mappers.RecipeToDtoMapper;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.models.User;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import com.kondziu.projects.TastyAppBackend.repos.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository,RecipesOverviewService recipesOverviewService,UserRepository userRepository){
        this.recipeRepository = recipeRepository;
        this.recipesOverviewService = recipesOverviewService;
        this.userRepository = userRepository;
    }

    @PostMapping("/recipes")
    public ResponseEntity<?> addRecipe(@RequestBody RecipeDto recipeDto){

        User user = userRepository.findById((long) recipeDto.getUserId())
                .orElseThrow( () -> new UserNotFoundException("user with given id not found: "+recipeDto.getUserId()));
        Recipe recipe = new Recipe(
                recipeDto.getName(),
                recipeDto.getLevel(),
                recipeDto.getTime(),
                recipeDto.getPortions(),
                recipeDto.getIngredients(),
                recipeDto.getSteps(),
                recipeDto.getDescription(),
                user);

        Recipe response=recipeRepository.save(recipe);
        return ResponseEntity.ok(RecipeToDtoMapper.recipeToDto(response));
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Integer id){
        Recipe recipe=recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("recipe with given id doesn't found : " +id));
        return  ResponseEntity.ok(RecipeToDtoMapper.recipeToDto(recipe));
    }

    @GetMapping(value = {"/recipes/overview","/recipes/overview/{optionalPage}"})
    public  ResponseEntity<RecipesOverviewWrapper> getRecipesOverview(@PathVariable Optional<Integer> optionalPage){
        return ResponseEntity.ok(recipesOverviewService.getRecipesOverview(optionalPage));
    }

}
