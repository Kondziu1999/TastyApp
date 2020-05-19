package com.kondziu.projects.TastyAppBackend.controllers;


import com.google.common.io.Files;
import com.kondziu.projects.TastyAppBackend.exceptions.BadRequestException;
import com.kondziu.projects.TastyAppBackend.exceptions.RecipeNotFoundException;
import com.kondziu.projects.TastyAppBackend.exceptions.UserNotFoundException;
import com.kondziu.projects.TastyAppBackend.models.ImageModel;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import com.kondziu.projects.TastyAppBackend.repos.UserRepository;
import com.kondziu.projects.TastyAppBackend.services.UploadImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final UploadImageService uploadImageService;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public FileUploadController(UploadImageService uploadImageService, UserRepository userRepository, RecipeRepository recipeRepository){
        this.uploadImageService=uploadImageService;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @PostMapping("/images/{optionalUserId}/{optionalRecipeId}")
    public ResponseEntity<?> uploadImage(@PathVariable Optional<Integer> optionalUserId,
                                         @PathVariable Optional<Integer> optionalRecipeId,
                                         @RequestParam("imageFile")MultipartFile file){

        //check if request is valid
        Integer userId=optionalUserId.orElseThrow( () ->new BadRequestException("user id not specified") );
        Integer recipeId=optionalRecipeId.orElseThrow( () -> new BadRequestException("recipe id not specified") );
        if(! userRepository.existsById(userId.longValue()) ) throw new UserNotFoundException("user with given id not found: "+userId);
        if(! recipeRepository.existsById(recipeId) ) throw new RecipeNotFoundException("file with given id not found: "+ recipeId);

        if(file.isEmpty()){
            throw new BadRequestException("the attached file is empty");
        }
        ImageModel imageModel=new ImageModel(UUID.randomUUID().toString(), Files.getFileExtension(file.getOriginalFilename()),file);
        boolean result = uploadImageService.uploadImage(imageModel,userId,recipeId);

        return result ? ResponseEntity.ok("image uploaded successfully") :
             ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
    }
}
