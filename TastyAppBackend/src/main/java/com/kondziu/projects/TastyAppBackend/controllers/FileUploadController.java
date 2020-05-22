package com.kondziu.projects.TastyAppBackend.controllers;


import com.google.common.io.Files;
import com.kondziu.projects.TastyAppBackend.dto.ImageDto;
import com.kondziu.projects.TastyAppBackend.exceptions.BadRequestException;
import com.kondziu.projects.TastyAppBackend.exceptions.RecipeNotFoundException;
import com.kondziu.projects.TastyAppBackend.exceptions.UserNotFoundException;
import com.kondziu.projects.TastyAppBackend.models.ImageModel;
import com.kondziu.projects.TastyAppBackend.payload.ApiResponse;
import com.kondziu.projects.TastyAppBackend.repos.RecipeRepository;
import com.kondziu.projects.TastyAppBackend.repos.UserRepository;
import com.kondziu.projects.TastyAppBackend.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {

    private final ImageService uploadImageService;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public FileUploadController(ImageService uploadImageService, UserRepository userRepository, RecipeRepository recipeRepository){
        this.uploadImageService=uploadImageService;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @PostMapping("/images/{optionalUserId}/{optionalRecipeId}")
    public ResponseEntity<?> uploadImages(@PathVariable Optional<Integer> optionalUserId,
                                         @PathVariable Optional<Integer> optionalRecipeId,
                                         @RequestParam("imageFile")MultipartFile[] files){

        //check if request is valid
        Integer userId = optionalUserId.orElseThrow( () ->new BadRequestException("user id not specified") );
        Integer recipeId = optionalRecipeId.orElseThrow( () -> new BadRequestException("recipe id not specified") );
        if(! userRepository.existsById(userId.longValue()) ) throw new UserNotFoundException("user with given id not found: "+userId);
        if(! recipeRepository.existsById(recipeId) ) throw new RecipeNotFoundException("file with given id not found: "+ recipeId);

        final boolean[] result={false};

        Arrays.stream(files).forEach( file -> {
            if(file.isEmpty()){
                throw new BadRequestException("the attached file is empty");
            }
            ImageModel imageModel=
                    new ImageModel(UUID.randomUUID().toString(), Files.getFileExtension(file.getOriginalFilename()),file);
            result[0] = uploadImageService.uploadImage(imageModel,userId,recipeId);
        });

        return result[0] ? ResponseEntity.ok().body(new ApiResponse(true, "photos uploaded successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
    }

    @GetMapping(value="/images/{optionalUserId}/{optionalRecipeId}")
    public  byte[] getPhoto(@PathVariable Optional<Integer> optionalUserId,
                                       @PathVariable Optional<Integer> optionalRecipeId){

        Integer userId = optionalUserId.orElseThrow( () ->new BadRequestException("user id not specified") );
        Integer recipeId= optionalRecipeId.orElseThrow( () -> new BadRequestException("recipe id not specified") );
        if(! userRepository.existsById(userId.longValue()) ) throw new UserNotFoundException("user with given id not found: "+userId);
        if(! recipeRepository.existsById(recipeId) ) throw new RecipeNotFoundException("file with given id not found: "+ recipeId);

        List<ImageDto> dtoList = uploadImageService.getImages(userId,recipeId);

//        return ResponseEntity.ok().body(dtoList.get(0));
        return dtoList.get(0).getBytes();
    }

    @GetMapping("/images/urls/{optionalUserId}/{optionalRecipeId}")
    public ResponseEntity<?> getRecipeImagesNames(@PathVariable Optional<Integer> optionalUserId,
                                                  @PathVariable Optional<Integer> optionalRecipeId){
        Integer userId = optionalUserId.orElseThrow( () -> new BadRequestException("user id not specified") );
        Integer recipeId= optionalRecipeId.orElseThrow( () -> new BadRequestException("recipe id not specified") );
        if(! userRepository.existsById(userId.longValue()) ) throw new UserNotFoundException("user with given id not found: "+userId);
        if(! recipeRepository.existsById(recipeId) ) throw new RecipeNotFoundException("file with given id not found: "+ recipeId);

        List<String> imagesNames = uploadImageService.getImagesNames(userId,recipeId);

        return imagesNames.size() > 0 ? ResponseEntity.ok().body(imagesNames) :
                ResponseEntity.badRequest().body("images to recipe don't exist");
    }

}
