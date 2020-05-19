package com.kondziu.projects.TastyAppBackend.controllers;


import com.kondziu.projects.TastyAppBackend.exceptions.BadRequestException;
import com.kondziu.projects.TastyAppBackend.models.ImageModel;
import com.kondziu.projects.TastyAppBackend.services.UploadImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private final UploadImageService uploadImageService;

    public FileUploadController(UploadImageService uploadImageService){
        this.uploadImageService=uploadImageService;
    }

    @PostMapping("/images/{optionalUserId}/{optionalRecipeId}")
    public ResponseEntity<?> uploadImage(@PathVariable Optional<Integer> optionalUserId, @PathVariable Optional<Integer> optionalRecipeId,
                                         @RequestParam("imageFile")MultipartFile file){

        Integer userId=optionalUserId.orElseThrow( () ->new BadRequestException("user id not specified") );
        Integer recipeId=optionalRecipeId.orElseThrow( () -> new BadRequestException("recipe id not specified") );
        if(file.isEmpty()){
            throw new BadRequestException("the attached file is empty");
        }
        ImageModel imageModel=new ImageModel(UUID.randomUUID().toString(),file.getContentType(),file);
        boolean result = uploadImageService.uploadImage(imageModel,userId,recipeId);

        return result ? ResponseEntity.ok("image uploaded successfully") :
             ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
    }
}
