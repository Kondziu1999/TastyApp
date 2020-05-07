package com.kondziu.projects.TastyAppBackend.controllers;

import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository){
        this.recipeRepository=recipeRepository;
    }

    @PostMapping("/recipes")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe){
        Recipe response=recipeRepository.save(recipe);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable Integer id){
        return Optional
                .ofNullable(recipeRepository.findById(id))
                .map(recipe-> ResponseEntity.ok().body(recipe))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
}
