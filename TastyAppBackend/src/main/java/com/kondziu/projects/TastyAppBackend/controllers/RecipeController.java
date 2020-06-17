package com.kondziu.projects.TastyAppBackend.controllers;


import com.kondziu.projects.TastyAppBackend.dto.CommentDto;
import com.kondziu.projects.TastyAppBackend.dto.RecipeDto;
import com.kondziu.projects.TastyAppBackend.dto.RecipesOverviewWrapper;
import com.kondziu.projects.TastyAppBackend.exceptions.RecipeNotFoundException;
import com.kondziu.projects.TastyAppBackend.exceptions.UserNotFoundException;
import com.kondziu.projects.TastyAppBackend.mappers.RecipeToDtoMapper;
import com.kondziu.projects.TastyAppBackend.models.Comment;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.models.User;
import com.kondziu.projects.TastyAppBackend.repos.CommentRepository;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import com.kondziu.projects.TastyAppBackend.repos.UserRepository;
import com.kondziu.projects.TastyAppBackend.services.CommentService;
import com.kondziu.projects.TastyAppBackend.services.RecipesOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {
    private RecipeRepository recipeRepository;
    private RecipesOverviewService recipesOverviewService;
    private UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository, RecipesOverviewService recipesOverviewService, UserRepository userRepository, CommentRepository commentRepository, CommentService commentService){
        this.recipeRepository = recipeRepository;
        this.recipesOverviewService = recipesOverviewService;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
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

    @PostMapping("recipes/{id}/comments")
    public ResponseEntity<?> addCommentToRecipe(@RequestBody CommentDto commentDto,@PathVariable Integer id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("recipe with given id not found: " + id));
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow( () -> new UserNotFoundException("user with given id not found"));

        Date date = new Date();
        Comment comment  = new Comment(commentDto.getComment(), date, recipe, user);

        commentRepository.save(comment);

        commentDto.setUsername(user.getUsername());
        commentDto.setDate(date);

        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("recipes/{id}/comments")
    public ResponseEntity<?> getRecipeComments(@PathVariable Long id){

        return ResponseEntity.ok(commentService.getRecipeComments(id));
    }

}
