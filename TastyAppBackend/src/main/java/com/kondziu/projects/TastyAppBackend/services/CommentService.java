package com.kondziu.projects.TastyAppBackend.services;

import com.kondziu.projects.TastyAppBackend.dto.CommentDto;
import com.kondziu.projects.TastyAppBackend.exceptions.RecipeNotFoundException;
import com.kondziu.projects.TastyAppBackend.mappers.CommentToDtoMapper;
import com.kondziu.projects.TastyAppBackend.models.Comment;
import com.kondziu.projects.TastyAppBackend.models.Recipe;
import com.kondziu.projects.TastyAppBackend.repos.CommentRepository;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final CommentToDtoMapper commentToDtoMapper;

    public CommentService(CommentRepository commentRepository, RecipeRepository recipeRepository, CommentToDtoMapper commentToDtoMapper) {
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
        this.commentToDtoMapper = commentToDtoMapper;
    }

    public List<CommentDto> getRecipeComments(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId.intValue()).orElseThrow( () -> new RecipeNotFoundException("recipe with given id not found: "+ recipeId));
        log.info("BEFORE");
        List<Comment> commentList = recipe.getComments();

        List<CommentDto> commentDtoList = commentList.stream()
                .map(commentToDtoMapper::commentToDto).collect(Collectors.toList());

        log.info("AFTER");

        return commentDtoList;
    }

}
