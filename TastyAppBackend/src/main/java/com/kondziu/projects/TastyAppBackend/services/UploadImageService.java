package com.kondziu.projects.TastyAppBackend.services;

import com.kondziu.projects.TastyAppBackend.models.ImageModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadImageService {

    private  final String UPLOADED_FOLDER="uploads";

    public boolean uploadImage(ImageModel imageModel, Integer userId, Integer recipeId){
        try {
            byte [] bytes = imageModel.getFile().getBytes();
            Path path = Paths.get(UPLOADED_FOLDER+"/users/"+ userId+"/recipes/"+recipeId+"/"
                    +imageModel.getUniqueName()
                    +"."
                    +imageModel.getType());

            Files.write(path,bytes);
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }


    //TODO compress image, consider using Tinypng
}
